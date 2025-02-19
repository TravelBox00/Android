package com.example.travelbox.presentation.view.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelbox.R
import com.example.travelbox.data.repository.search.SearchRepository
import com.example.travelbox.databinding.FragmentSearchBinding

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
    private val searchRepository = SearchRepository()
    private lateinit var suggestionsAdapter: SuggestionsAdapter
    private lateinit var searchPostViewModel: SearchPostViewModel

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

        searchPostViewModel = ViewModelProvider(requireActivity())[SearchPostViewModel::class.java]

        setupSearchSuggestions() // 자동완성
        setupSearchButton() // 검색

        // 인기 검색어 어댑터 설정
        val popularAdapter = SuggestionsAdapter(popularSearches) { selectedSuggestion ->
            binding.etSearch.setText(selectedSuggestion)
        }

        binding.popularSearchesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = popularAdapter
        }

        binding.ivSearch.setOnClickListener {
            val keyword = binding.etSearch.text.toString()
            if (keyword.isNotEmpty()) {
                searchForPosts(keyword)
            } else {
                Toast.makeText(requireContext(), "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

    }


    // 검색어 자동 완성
    private fun setupSearchSuggestions() {
        suggestionsAdapter = SuggestionsAdapter(emptyList()) { selectedSuggestion ->
            binding.etSearch.setText(selectedSuggestion)
        }

        binding.suggestionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = suggestionsAdapter
            visibility = View.GONE
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                if (query.isNotEmpty()) {
                    AutoCompleteSuggestions(query) // 자동완성 API 호출
                } else {
                    binding.suggestionsRecyclerView.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun AutoCompleteSuggestions(query: String) {
        searchRepository.getSearchWord(query) { response ->
            if (response != null && response.result.isNotEmpty()) {
                suggestionsAdapter.updateSuggestions(response.result)
                binding.suggestionsRecyclerView.visibility = View.VISIBLE
            } else {
                binding.suggestionsRecyclerView.visibility = View.GONE
            }
        }
    }

    private fun setupSearchButton() {
        binding.ivSearch.setOnClickListener {
            val searchText = binding.etSearch.text.toString().trim()
            if (searchText.isNotEmpty()) {
                searchForPosts(searchText)
            } else {
                Toast.makeText(requireContext(), "검색어를 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun searchForPosts(searchKeyword: String) {
        searchRepository.getSearchPost(searchKeyword, 0) { threadPosts ->
            val posts = threadPosts ?: emptyList()
            searchPostViewModel.setPosts(posts)

            val searchPostFragment = SearchPostFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, searchPostFragment)
                .addToBackStack(null)
                .commit()

            if (posts.isEmpty()) {
                Toast.makeText(requireContext(), "게시물을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}