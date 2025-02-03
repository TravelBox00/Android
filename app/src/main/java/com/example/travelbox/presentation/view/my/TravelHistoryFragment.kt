package com.example.travelbox.presentation.view.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.travelbox.databinding.FragmentStoryBinding
import com.example.travelbox.databinding.FragmentTravelHistoryBinding

class TravelHistoryFragment : Fragment() {

    private var _binding: FragmentTravelHistoryBinding? = null
    private val binding get() = _binding!!

    // 이미지를 담을 리스트 (예시로 URL 넣음)
    private val imageList = listOf(
        "https://example.com/image1.jpg",
        "https://example.com/image2.jpg",
        "https://example.com/image3.jpg",
        "https://example.com/image4.jpg",
        "https://example.com/image5.jpg",
        "https://example.com/image6.jpg"
    )

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
    }

    private fun setupRecyclerView() {
        val adapter = HistoryAdapter(imageList)
        binding.recyclerHistory.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerHistory.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}