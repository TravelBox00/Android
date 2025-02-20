package com.example.travelbox.presentation.view.my

import FollowingAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelbox.data.network.ApiNetwork
import com.example.travelbox.data.repository.my.FollowingItem
import com.example.travelbox.data.repository.my.MyRepository
import com.example.travelbox.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    private val itemList = mutableListOf<FollowingItem>()
    private lateinit var adapter: FollowingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        setupRecyclerView() // 🔹 RecyclerView 설정 먼저 실행
        loadFollowing() // 🔹 데이터 로딩 실행
        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = FollowingAdapter(itemList) { followItem ->
            // 버튼 클릭 시 isFollowedByThem 값을 반전
            followItem.isFollowedByThem = !followItem.isFollowedByThem
            adapter.notifyDataSetChanged()
        }

        binding.recyclerFollowing.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerFollowing.adapter = adapter
    }

    private fun loadFollowing() {
        // val userTag = "actualUserTag" // 🔹 실제 로그인된 사용자 태그로 변경 필요
        val userTag = ApiNetwork.getUserTag().toString()


        MyRepository.getFollowing(userTag) { followings ->
            if (followings != null) {
                requireActivity().runOnUiThread {
                    adapter.updateList(followings) // 🔹 UI 스레드에서 데이터 업데이트
                }
            } else {
                Log.e("FollowingFragment", "팔로잉 데이터를 불러오는 데 실패했습니다.")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
