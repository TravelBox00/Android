package com.example.mypage

import CommentFragment
import FollowingFragment
import InformationFragment
import StoryFragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.travelbox.R
import com.example.travelbox.databinding.FragmentMypageBinding
import com.example.travelbox.presentation.view.my.FollowerFragment
import com.example.travelbox.presentation.view.my.ScrapFragment
import com.google.android.material.tabs.TabLayoutMediator

class MypageFragment : Fragment() {
    private var _binding: FragmentMypageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtonListeners()
        setupViewPagerAndTabs()
    }

    private fun setupButtonListeners() {
        //게시글
        binding.storyBtn.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.main_frm, StoryFragment()) // 'fragment_container'는 이동할 컨테이너의 ID입니다.
            fragmentTransaction.addToBackStack(null) // 뒤로 가기 스택에 추가
            fragmentTransaction.commit()
        }
        //팔로워
        binding.followerBtn.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.main_frm, FollowerFragment())
            fragmentTransaction.addToBackStack(null) // 뒤로 가기
            fragmentTransaction.commit()
        }
        //팔로잉
        binding.followingBtn.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.main_frm, FollowingFragment())
            fragmentTransaction.addToBackStack(null) // 뒤로 가기
            fragmentTransaction.commit()
        }
        //스크랩
        binding.scrapButton.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.main_frm, ScrapFragment())
            fragmentTransaction.addToBackStack(null) // 뒤로 가기
            fragmentTransaction.commit()
        }
        //나의 댓글
        binding.commentButton.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.main_frm, CommentFragment())
            fragmentTransaction.addToBackStack(null) // 뒤로 가기
            fragmentTransaction.commit()
        }
        //회원정보 수정
        binding.profileEditButton.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.main_frm, InformationFragment())
            fragmentTransaction.addToBackStack(null) // 뒤로 가기
            fragmentTransaction.commit()
        }




    }

    private fun setupViewPagerAndTabs() {
        // ViewPager2 Adapter 설정
        val pagerAdapter = MypagePagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter

        // TabLayout과 ViewPager2 연결
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "여행 기록"
                1 -> tab.text = "기념품"
                2 -> tab.text = "여행지"
                3 -> tab.text = "여행 코디"
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
