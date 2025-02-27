package com.example.mypage

import CommentFragment
import InformationFragment
import StoryFragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.travelbox.R
import com.example.travelbox.data.network.ApiNetwork
import com.example.travelbox.data.repository.my.MyRepository
import com.example.travelbox.data.repository.my.UserInfo
import com.example.travelbox.databinding.FragmentMypageBinding
import com.example.travelbox.presentation.view.my.FollowerFragment
import com.example.travelbox.presentation.view.my.FollowingFragment
import com.example.travelbox.presentation.view.my.ScrapFragment
import com.google.android.material.tabs.TabLayoutMediator

class MypageFragment : Fragment() {
    private var _binding: FragmentMypageBinding? = null
    private val binding get() = _binding!!
    private lateinit var userTag: String //유저태그

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

        //userTag = "john#123"
        userTag = ApiNetwork.getUserTag().toString()

        loadUserInfo()//사용자 정보
        getPostsAndUpdateUI() //게시글 수 조회
        getFollowersAndUpdateUI() //팔로워 수 조회
        getFollowingAndUpdateUI() //팔로잉 수 조회
    }

    //유저 화면 업로드
    private fun loadUserInfo() {
        MyRepository.getUserInfo { userInfo ->
            if (userInfo != null) {
                updateUI(userInfo)
            } else {
                Log.e("MypageFragment", "Failed to load user information")
            }
        }
    }

    private fun updateUI(userInfo: UserInfo) {
        // Set the profile image using Glide
        Glide.with(this)
            .load(userInfo.userProfileImage)
            .into(binding.profileIV)

        // Set the nickname and user tag
        binding.textNickname.text = userInfo.userNickname
        binding.nameTag.text = userInfo.userTag
    }


    private fun getPostsAndUpdateUI() {
        MyRepository.fetchPosts { posts ->
            val postCount = posts?.size ?: 0 // 게시글 수를 계산
            // storyBtn에 게시글 수를 표시
            binding.storyBtn.text = "$postCount"
            Log.d("MypageFragment", "게시글 수: $postCount")
        }
    }

    private fun getFollowersAndUpdateUI() {
        // 팔로워 목록을 가져오고, 버튼에 팔로워 수를 업데이트
        MyRepository.getFollowers(userTag) { followers ->
            val followerCount = followers?.size ?: 0 // 팔로워 수를 계산
            // followerBtn에 팔로워 수를 표시
            binding.followerBtn.text = "$followerCount"
            Log.d("MypageFragment", "팔로워 수: $followerCount")
        }
    }
    private fun getFollowingAndUpdateUI() {
        MyRepository.getFollowing(userTag) { followings ->
            val followingCount = followings?.size ?: 0
            binding.followingBtn.text = "$followingCount"
            Log.d("MypageFragment", "팔로잉 수: $followingCount")
        }
    }

    private fun setupButtonListeners() {
        // 게시글
        binding.storyBtn.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.main_frm, StoryFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        // 팔로워
        binding.followerBtn.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.main_frm, FollowerFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        // 팔로잉
        binding.followingBtn.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.main_frm, FollowingFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        // 스크랩
        binding.scrapButton.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.main_frm, ScrapFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        // 나의 댓글
        binding.commentButton.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.main_frm, CommentFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        // 회원정보 수정
        binding.profileEditButton.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.main_frm, InformationFragment())
            fragmentTransaction.addToBackStack(null)
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
