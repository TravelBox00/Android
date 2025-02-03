package com.example.travelbox.presentation.view.my

import FollowItem
import FollowingAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelbox.databinding.FragmentFollowerBinding

class FollowerFragment : Fragment() {

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!

    // 팔로워 리스트 (데이터 예시)
    private val itemList = mutableListOf(
        FollowItem("https://example.com/user1.jpg", "이름1", "user1", true),
        FollowItem("https://example.com/user2.jpg", "이름2", "user2", false)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        val adapter = FollowingAdapter(itemList) { followItem ->
            // 팔로워 버튼 클릭 시 팔로잉 상태 변경
            followItem.isFollowing = !followItem.isFollowing
            // 데이터 변경 후 RecyclerView 갱신
            //adapter.notifyDataSetChanged()
        }

        binding.recyclerFollower.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerFollower.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
