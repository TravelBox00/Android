package com.example.travelbox.presentation.view.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelbox.R
import com.example.travelbox.data.repository.home.HomeRepository
import com.example.travelbox.databinding.ActivityFilterBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class FilterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFilterBinding

    // cityDistrictMap을 나중에 초기화하기 위해 lateinit 선언
    private lateinit var cityDistrictMap: Map<String, List<String>>

//    private val cityDistrictMap = mapOf(
//        "서울" to listOf("강남", "명동", "홍대", "이태원"),
//        "부산" to listOf("해운대", "서면", "광안리", "자갈치시장")
//    )

    private var categoryString: String? = "여행"

    private var selectedCity: String? = null
    private var selectedDistrict: String? = null

    private lateinit var keywordCard: List<CardView>
    private lateinit var keywordTv: List<TextView>
    //private lateinit var clickedStates: MutableList<Boolean>
    private var selectedKeywordIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // JSON 파일에서 cityDistrictMap 데이터 로드
        cityDistrictMap = loadCityDistrictMap()

        // 카테고리 카드뷰 리스트
        keywordCard = listOf(
            binding.cvKeywordAll,
            binding.cvKeywordRecord,
            binding.cvKeywordSouvenir,
            binding.cvKeywordPlace,
            binding.cvKeywordStyle
        )

        // 카테고리 텍스트뷰 리스트

        keywordTv = listOf(
            binding.tvAll,
            binding.tvRecord,
            binding.tvSouvenir,
            binding.tvPlace,
            binding.tvStyle
        )


        // 클릭 상태를 저장하는 리스트
        //clickedStates = MutableList(keywordCard.size) { false }

        keywordCard.forEachIndexed { index, cardView ->
            cardView.setOnClickListener {

                // 상태를 토글 후, 업데이트
                //clickedStates[index] = toggleCardViewState(cardView, keywordTv[index], clickedStates[index])
                selectSingleKeyword(index)

            }
        }

        // 리사이클러뷰 출력

        setupRecyclerView(binding.rvCountry, listOf("국내", "해외")) { selected ->
            if (selected == "국내") showCities(cityDistrictMap.keys.toList()) else hideCities()
        }

        // 확인 버튼 클릭

        binding.btnCheck.setOnClickListener { sendFiltersToBestPostFragment() }
    }

    //  하나의 카테고리만 선택되도록 처리하는 함수
    private fun selectSingleKeyword(index: Int) {
        if (selectedKeywordIndex != -1) {
            // 이전 선택 해제
            keywordCard[selectedKeywordIndex].setCardBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
            keywordTv[selectedKeywordIndex].setTextColor(ContextCompat.getColor(this, R.color.gray3))
        }

        // 현재 선택 반영
        selectedKeywordIndex = index
        keywordCard[index].setCardBackgroundColor(ContextCompat.getColor(this, R.color.green_main))
        keywordTv[index].setTextColor(ContextCompat.getColor(this, R.color.white))
    }


    // 키워드 토글 선택 함수
    private fun toggleCardViewState(cardView: CardView, textView: TextView, isClicked: Boolean) :Boolean {
        return if (isClicked) {
            // 클릭 해제 상태
            cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context, android.R.color.transparent))
            textView.setTextColor(ContextCompat.getColor(textView.context, R.color.gray3))
            false
        } else {
            // 클릭 상태
            cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context, R.color.green_main))
            textView.setTextColor(ContextCompat.getColor(textView.context, R.color.white))
            true
        }
    }

//    private fun sendFiltersToBestPostFragment() {
//        if (selectedCity != null && selectedDistrict != null) {
//
//            // 선택된 카테고리 필터링
//            val selectedCategories = keywordTv
//                .filterIndexed { index, _ -> clickedStates[index] }  // 선택된 항목만 필터링
//                .map { it.text.toString() }  // 텍스트 값 추출
//
//            // 선택된 카테고리를 문자열로 변환 (예: "기록, 기념품")
//            categoryString = if (selectedCategories.isNotEmpty()) selectedCategories.joinToString(", ") else ""
//
//
//            val intent = Intent().apply {
//                putExtra("category", categoryString)
//                putExtra("city", selectedCity)
//                putExtra("district", selectedDistrict)
//            }
//            setResult(RESULT_OK, intent)
//            finish()
//        } else {
//            Toast.makeText(this, "지역과 구/군을 선택해주세요", Toast.LENGTH_SHORT).show()
//        }
//    }


    private fun sendFiltersToBestPostFragment() {
        if (selectedCity != null && selectedDistrict != null) {
            categoryString = if (selectedKeywordIndex != -1) keywordTv[selectedKeywordIndex].text.toString() else "전체"

            val intent = Intent().apply {
                putExtra("category", categoryString)
                putExtra("city", selectedCity)
                putExtra("district", selectedDistrict)
            }
            setResult(RESULT_OK, intent)
            finish()
        } else {
            Toast.makeText(this, "지역과 구/군을 선택해주세요", Toast.LENGTH_SHORT).show()
        }
    }




    private fun setupRecyclerView(recyclerView: RecyclerView, items: List<String>, onItemSelected: (String) -> Unit) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FilterAdapter(items, onItemSelected)
        recyclerView.visibility = View.VISIBLE
    }


    private fun fetchFilteredPosts() {
        if (selectedCity != null && selectedDistrict != null) {
            HomeRepository.regionFilterSearch("여행", selectedCity!!, null) { response ->
                if (response?.isSuccess == true) {

                    Log.d("지역 필터", "데이터 조회 성공 :$response")

                    startActivity(Intent(this, BestPostFragment::class.java).apply {
                        putExtra("category", "여행")
                        putExtra("region", selectedCity)
                    })
                } else Log.e("지역 필터", "데이터 조회 실패")
            }
        }
    }

    // 도시 출력 함수

//    private fun showCities(cities: List<String>) {
//        setupRecyclerView(binding.rvCity, cities) { city ->
//            selectedCity = city
//            showDistricts(cityDistrictMap[city] ?: emptyList())
//        }
//    }
//
//    // 도시 내 장소 출력 함수
//    private fun showDistricts(districts: List<String>) {
//        setupRecyclerView(binding.rvDistrict, districts) { district ->
//            selectedDistrict = district
//            Toast.makeText(this, "$district 선택됨", Toast.LENGTH_SHORT).show()
//        }
//    }
//  하나의 도시만 선택되도록 수정
    private fun showCities(cities: List<String>) {
        setupRecyclerView(binding.rvCity, cities) { city ->
            if (selectedCity != city) {  // 기존 선택과 다를 경우에만 변경
                selectedCity = city
                selectedDistrict = null  // 🔹 새 도시 선택 시 기존 지역 초기화
                showDistricts(cityDistrictMap[city] ?: emptyList())
            }
        }
    }

    //  하나의 지역만 선택되도록 수정
    private fun showDistricts(districts: List<String>) {
        setupRecyclerView(binding.rvDistrict, districts) { district ->
            if (selectedDistrict != district) {  // 기존 선택과 다를 경우에만 변경
                selectedDistrict = district
                Toast.makeText(this, "$district 선택됨", Toast.LENGTH_SHORT).show()
            }
        }
    }


    // 도시 숨기기

    private fun hideCities() {
        binding.rvCity.visibility = RecyclerView.GONE
        hideDistricts()
    }

    // 도시 내 장소 숨기기

    private fun hideDistricts() {
        binding.rvDistrict.visibility = RecyclerView.GONE
    }

    // 🔹 JSON 파일을 읽어와 cityDistrictMap을 생성하는 함수
    private fun loadCityDistrictMap(): Map<String, List<String>> {
        return try {
            val inputStream = assets.open("filter.json") // assets 폴더에서 JSON 파일 열기
            val json = inputStream.bufferedReader().use { it.readText() } // JSON 문자열 읽기

            val type = object : TypeToken<Map<String, List<Map<String, Any>>>>() {}.type
            val parsedJson: Map<String, List<Map<String, Any>>> = Gson().fromJson(json, type)

            val domesticCities = parsedJson["domestic"] ?: emptyList()

            domesticCities.associate { cityData ->
                val city = cityData["city"] as String
                val areas = cityData["areas"] as List<String>
                city to areas
            }
        } catch (e: IOException) {
            Log.e("FilterActivity", "JSON 파일을 읽을 수 없습니다.", e)
            emptyMap() // 오류 발생 시 빈 맵 반환
        }
    }
}
