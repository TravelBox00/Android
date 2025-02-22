package com.example.travelbox.presentation.view.my

import FollowerAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelbox.data.network.ApiNetwork
import com.example.travelbox.data.repository.my.MyRepository
import com.example.travelbox.databinding.FragmentFollowerBinding
import com.example.travelbox.data.repository.my.FollowerItem

class FollowerFragment : Fragment() {

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!

    private val itemList = mutableListOf<FollowerItem>()
    private lateinit var adapter: FollowerAdapter  // 🔹 FollowingAdapter → FollowerAdapter 로 변경

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        setupRecyclerView()
        loadFollowers() // 🔹 API 호출하여 팔로워 목록 가져오기
        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = FollowerAdapter(itemList) { followItem ->
            // 팔로워 버튼 클릭 시 팔로잉 상태 변경
            followItem.isFollowing = !followItem.isFollowing
            adapter.notifyDataSetChanged() // 변경된 내용 RecyclerView에 반영
        }

        binding.recyclerFollower.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerFollower.adapter = adapter
    }

    private fun loadFollowers() {
        //val userTag = "actualUserTag" // 🔹 실제 사용자 태그로 변경 필요
        val userTag = ApiNetwork.getUserTag().toString()

        MyRepository.getFollowers(userTag) { followers ->
            if (followers != null) {
                adapter.updateList(followers) // 🔹 adapter 데이터 업데이트
            } else {
                Log.e("FollowerFragment", "팔로워 데이터를 불러오는 데 실패했습니다.")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
