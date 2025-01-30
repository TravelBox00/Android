package com.example.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.travelbox.R
import com.example.travelbox.databinding.FragmentMypageBinding
import com.example.travelbox.presentation.view.my.ScrapFragment
import com.example.travelbox.presentation.view.my.CommentFragment
import com.example.travelbox.presentation.view.my.ProfileEditFragment
import com.google.android.material.tabs.TabLayoutMediator

class MypageFragment : Fragment() {
    private var _binding: FragmentMypageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 뷰 바인딩 초기화
        _binding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPagerAndTabs()

        // 버튼 클릭 리스너 설정
        binding.root.findViewById<Button>(R.id.scrapButton).setOnClickListener {
            replaceFragment(ScrapFragment())
        }

        binding.root.findViewById<Button>(R.id.commentButton).setOnClickListener {
            replaceFragment(CommentFragment())
        }

        binding.root.findViewById<Button>(R.id.profileEditButton).setOnClickListener {
            replaceFragment(ProfileEditFragment())
        }
    }

    private fun setupViewPagerAndTabs() {
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        val pagerAdapter = MypagePagerAdapter(this)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "여행 기록"
                1 -> tab.text = "기념품"
                2 -> tab.text = "여행지"
                3 -> tab.text = "여행 코디"
            }
        }.attach()
    }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.commit {
            replace(R.id.fragmentContainer, fragment)
            addToBackStack(null) // 뒤로 가기 지원
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
