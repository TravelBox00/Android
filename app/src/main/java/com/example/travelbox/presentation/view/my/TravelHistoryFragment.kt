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

class TravelHistoryFragment : Fragment() {

    private var _binding: FragmentTravelHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTravelHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadTravelHistory()
    }

    private fun loadTravelHistory() {
        // MyRepository.getMyThreads로 호출
        MyRepository.getMyThreads { threadList ->
            if (threadList != null) {
                setupRecyclerView(threadList)  // threadList를 어댑터에 전달
            } else {
                // 데이터가 없거나 에러가 발생한 경우 처리
                Log.e("TravelHistoryFragment", "여행 스레드 로드 실패")
            }
        }
    }

    private fun setupRecyclerView(threadList: List<ThreadData>) {
        val adapter = HistoryAdapter(threadList)
        binding.recyclerHistory.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerHistory.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

