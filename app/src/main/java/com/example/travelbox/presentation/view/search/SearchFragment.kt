package com.example.travelbox.presentation.view.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelbox.R
import com.example.travelbox.databinding.FragmentSearchBinding
import com.example.travelbox.presentation.view.home.BestPostFragment
import com.example.travelbox.presentation.view.search.SearchPostFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    // 추천 검색어 샘플 데이터
    private val searchSuggestions = listOf(
        "오사카 전체", "오사카 맛집", "오사카 근교", "오사카 도톤보리",
        "오사카 스시", "오사카 교토 차이", "오사카 유니버설", "오사카 하루카스", "오사카성"
    )

    // 인기 검색어 샘플 데이터
    private val popularSearches = listOf(
        "도쿄 시부야", "상하이", "유럽 여행", "제주도"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 추천 검색어 어댑터 설정
        val suggestionsAdapter = SuggestionsAdapter(emptyList()) { selectedSuggestion ->
            binding.etSearch.setText(selectedSuggestion)
        }

        binding.suggestionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = suggestionsAdapter
            visibility = View.GONE
        }

        // 검색어 입력 시 추천 검색어 필터링
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                if (query.isNotEmpty()) {
                    val filteredSuggestions =
                        searchSuggestions.filter { it.contains(query, ignoreCase = true) }
                    suggestionsAdapter.updateSuggestions(filteredSuggestions)
                    binding.suggestionsRecyclerView.visibility = View.VISIBLE
                } else {
                    binding.suggestionsRecyclerView.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // 인기 검색어 어댑터 설정
        val popularAdapter = SuggestionsAdapter(popularSearches) { selectedSuggestion ->
            binding.etSearch.setText(selectedSuggestion)
        }

        binding.popularSearchesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = popularAdapter
        }

        // 검색 버튼 클릭 시
        binding.ivSearch.setOnClickListener {
            //val searchText = binding.etSearch.text.toString()
            //if (searchText.isNotEmpty()) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, SearchPostFragment())
                    .addToBackStack(null)
                    .commit()
            //}
        }

    }

}