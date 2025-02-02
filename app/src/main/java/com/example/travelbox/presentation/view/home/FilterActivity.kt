package com.example.travelbox.presentation.view.home

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelbox.R
import com.example.travelbox.databinding.ActivityFilterBinding

class FilterActivity : AppCompatActivity() {

    private lateinit var rvCountry: RecyclerView
    private lateinit var rvCity: RecyclerView
    private lateinit var rvDistrict: RecyclerView

    private lateinit var binding: ActivityFilterBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 카테고리 카드뷰 리스트(테스트 데이터)
        val keywordCard = listOf(
            binding.cvKeywordAll,
            binding.cvKeywordRecord,
            binding.cvKeywordSouvenir,
            binding.cvKeywordPlace,
            binding.cvKeywordStyle
        )

        // 카테고리 텍스트뷰 리스트(테스트 데이터)

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
                // 상태를 토글하고 업데이트
                clickedStates[index] = toggleCardViewState(cardView, keywordTv[index], clickedStates[index])
            }
        }

        // RecyclerView 초기화
        rvCountry = binding.rvCountry
        rvCity = binding.rvCity
        rvDistrict = binding.rvDistrict


        // 테스트 데이터
        val countris = listOf("국내", "해외")
        val domesticCities = listOf("서울", "부산", "제주", "대구", "인천", "광주", "대전", "울산", "수원", "광주", "대전", "울산", "수원")
        val detailDistricts = listOf("한라산", "성산일출봉", "협재해변", "우도", "애월")



        rvCountry.layoutManager = LinearLayoutManager(this)
        rvCountry.adapter = FilterAdapter(countris) { selectedCountry ->
            // 국내 선택
            if (selectedCountry == "국내") {
                showCities(domesticCities)
            } else {
                hideCities()
            }

        }


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


    // 도시 선택
    private fun showCities(cities: List<String>) {
        //binding.tvCity.visibility = View.VISIBLE
        rvCity.visibility = View.VISIBLE
        rvCity.layoutManager = LinearLayoutManager(this)

        rvCity.adapter = FilterAdapter(cities) { selectedCity ->
            if (selectedCity == "제주") {
                showDistricts(listOf("한라산", "성산일출봉", "협재해변", "우도", "애월"))
            } else {
                hideDistricts()
            }

        }
    }


    private fun showDistricts(districts: List<String>) {
        //binding.tvDistrict.visibility = View.VISIBLE
        rvDistrict.visibility = View.VISIBLE
        rvDistrict.layoutManager = LinearLayoutManager(this)
        rvDistrict.adapter = FilterAdapter(districts) { district ->
            Toast.makeText(this, "$district 선택됨", Toast.LENGTH_SHORT).show()
        }

    }


    private fun hideCities() {
        //binding.tvCity.visibility = View.GONE
        rvCity.visibility = View.GONE
        hideDistricts()
    }

    private fun hideDistricts() {
        //binding.tvDistrict.visibility = View.GONE
        rvDistrict.visibility = View.GONE
    }
}