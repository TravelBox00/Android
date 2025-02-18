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

class FilterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFilterBinding

    private val cityDistrictMap = mapOf(
        "서울" to listOf("강남", "명동", "홍대", "이태원"),
        "부산" to listOf("해운대", "서면", "광안리", "자갈치시장")
    )

    private var selectedCity: String? = null
    private var selectedDistrict: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 카테고리 카드뷰 리스트
        val keywordCard = listOf(
            binding.cvKeywordAll,
            binding.cvKeywordRecord,
            binding.cvKeywordSouvenir,
            binding.cvKeywordPlace,
            binding.cvKeywordStyle
        )

        // 카테고리 텍스트뷰 리스트

        val keywordTv = listOf(
            binding.tvAll,
            binding.tvRecord,
            binding.tvSouvenir,
            binding.tvPlace,
            binding.tvStyle
        )


        // 클릭 상태를 저장하는 리스트
        val clickedStates = MutableList(keywordCard.size) { false }

        keywordCard.forEachIndexed { index, cardView ->
            cardView.setOnClickListener {

                // 상태를 토글 후, 업데이트
                clickedStates[index] = toggleCardViewState(cardView, keywordTv[index], clickedStates[index])
            }
        }

        // 리사이클러뷰 출력

        setupRecyclerView(binding.rvCountry, listOf("국내", "해외")) { selected ->
            if (selected == "국내") showCities(cityDistrictMap.keys.toList()) else hideCities()
        }

        // 확인 버튼 클릭

        binding.btnCheck.setOnClickListener { sendFiltersToBestPostFragment() }
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

    private fun sendFiltersToBestPostFragment() {
        if (selectedCity != null && selectedDistrict != null) {
            val intent = Intent().apply {
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
            HomeRepository.regionFilterSearch("여행", selectedCity!!) { response ->
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

    private fun showCities(cities: List<String>) {
        setupRecyclerView(binding.rvCity, cities) { city ->
            selectedCity = city
            showDistricts(cityDistrictMap[city] ?: emptyList())
        }
    }

    // 도시 내 장소 출력 함수
    private fun showDistricts(districts: List<String>) {
        setupRecyclerView(binding.rvDistrict, districts) { district ->
            selectedDistrict = district
            Toast.makeText(this, "$district 선택됨", Toast.LENGTH_SHORT).show()
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
}
