package com.example.travelbox.presentation.view.my

import TempoAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.travelbox.databinding.FragmentDestinationsBinding

class DestinationsFragment : Fragment() {

    private var _binding: FragmentDestinationsBinding? = null
    private val binding get() = _binding!!

    // 예제 이미지 URL 리스트
    private val imageList = listOf(
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ4bMlITRFWvyJoEMD5yFyONCnZwXG84nN9NQ&s",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRfj9olAOpmnJmPfh0Hl_AKCvlkCQbcbNLgeQ&s",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQsDDadJprncGtViBuHvaqGWmh7PeYlaw3MrQ&s",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ4a3w_jIDVDDRV1dQiGeP1cI-dmMgo6VyLyA&s0",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTw9BOpWjbSqM7Sx631cUrRnCXodVGg1Indqw&s",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSJF83kWa5lo_ouaPhUL0khG7zycqMP3eV6-Q&s",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTuGWj1FRlbWw2CUzBJzgspghAKzA3KwVDutw&s",
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDestinationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val adapter = TempoAdapter(imageList)
        binding.recyclerDestinations.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerDestinations.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
