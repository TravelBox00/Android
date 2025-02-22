package com.example.travelbox.presentation.view.my

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.travelbox.data.repository.my.MyRepository
import com.example.travelbox.data.repository.my.ThreadData
import com.example.travelbox.databinding.FragmentTravelHistoryBinding
import androidx.lifecycle.lifecycleScope
import com.example.travelbox.data.network.ApiNetwork.getAccessToken
import kotlinx.coroutines.launch

class TravelHistoryFragment : Fragment() {

    private var _binding: FragmentTravelHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTravelHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        loadTravelHistory()
    }

    private fun loadTravelHistory() {
        val token = "Bearer " + getAccessToken()

        viewLifecycleOwner.lifecycleScope.launch {
            val threadList = MyRepository.getMyThreads(token) // ✅ 수정된 API 호출
            if (threadList != null) {
                adapter.updateThreads(threadList)
            } else {
                Log.e("API_ERROR", "여행 스레드 로드 실패 - 응답이 null입니다.")
            }
        }
    }




    private fun setupRecyclerView() {
        adapter = HistoryAdapter()
        binding.recyclerHistory.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerHistory.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
