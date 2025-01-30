package com.example.travelbox.presentation.view.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.travelbox.databinding.FragmentMypageTabBinding

class MypageTabFragment : Fragment() {

    companion object {
        private const val ARG_POSITION = "position"

        fun newInstance(position: Int): MypageTabFragment {
            val fragment = MypageTabFragment()
            val args = Bundle()
            args.putInt(ARG_POSITION, position)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMypageTabBinding.inflate(inflater, container, false)
        val position = arguments?.getInt(ARG_POSITION) ?: 0
        // 각 탭에 대한 내용 설정
        // 예: position에 따라 다른 데이터를 보여줄 수 있습니다.
        return binding.root
    }
}
