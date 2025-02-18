package com.example.travelbox.presentation.view.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.travelbox.R
import com.example.travelbox.databinding.FragmentTravelStylingBinding

class TravelStylingFragment : Fragment() {

    private var _binding: FragmentTravelStylingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 뷰 바인딩 초기화
        _binding = FragmentTravelStylingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 샘플 데이터
        val sampleData = listOf(
            PhotoItem(R.drawable.img, "스타일 1"),
            PhotoItem(R.drawable.img, "스타일 2"),
            PhotoItem(R.drawable.img, "스타일 3"),
            PhotoItem(R.drawable.img, "스타일 4")
        )

        // RecyclerView 설정
        val adapter = PhotoAdapter(sampleData)
        binding.stylingRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 3) // 3열 그리드
            this.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 뷰 바인딩 해제
        _binding = null
    }
}
