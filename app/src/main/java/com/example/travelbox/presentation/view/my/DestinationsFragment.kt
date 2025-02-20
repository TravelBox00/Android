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
        "https://www.google.com/imgres?q=%EC%97%AC%ED%96%89&imgurl=https%3A%2F%2Fcloudfront-ap-northeast-1.images.arcpublishing.com%2Fchosun%2FCU422HY2YJHS7JQ3REJ24E4H64.PNG&imgrefurl=https%3A%2F%2Fwww.chosun.com%2Finternational%2Ftopic%2F2022%2F09%2F03%2FGTZPBPBWA5C5JHQDUJMAEJOWEM%2F&docid=Xklc7xy7qCa3OM&tbnid=WuolzhtYkfT0iM&vet=12ahUKEwjEztHtxdKLAxU3r1YBHdoAIPIQM3oECBkQAA..i&w=1128&h=759&hcb=2&ved=2ahUKEwjEztHtxdKLAxU3r1YBHdoAIPIQM3oECBkQAA",
        "https://www.google.com/imgres?q=%EC%97%AC%ED%96%89&imgurl=http%3A%2F%2Funglobalcompact.kr%2Fwp-content%2Fuploads%2F2023%2F07%2Fpexels-asad-photo-maldives-1450353-1024x680.jpg&imgrefurl=http%3A%2F%2Funglobalcompact.kr%2Fesg-%25EB%258F%2599%25ED%2596%25A5-%25EC%25A7%2580%25EC%2586%258D%25EA%25B0%2580%25EB%258A%25A5%25ED%2595%259C-%25EC%2597%25AC%25ED%2596%2589%2F&docid=tkDGVOnScD7o-M&tbnid=sGo937UCfKVFEM&vet=12ahUKEwjEztHtxdKLAxU3r1YBHdoAIPIQM3oFCIYBEAA..i&w=1024&h=680&hcb=2&ved=2ahUKEwjEztHtxdKLAxU3r1YBHdoAIPIQM3oFCIYBEAA",
        "https://example.com/image3.jpghttps://www.google.com/imgres?q=%EC%97%AC%ED%96%89&imgurl=https%3A%2F%2Fres.klook.com%2Fimage%2Fupload%2Fq_85%2Fc_fill%2Cw_750%2Fv1617101647%2Fblog%2Fedlhmuf96dpqcnodl9qf.jpg&imgrefurl=https%3A%2F%2Fwww.klook.com%2Fko%2Fblog%2Fmust-visit-ulleung-spots%2F&docid=qgy3gy2ffodPlM&tbnid=zhDMuzoQX6RmHM&vet=12ahUKEwjEztHtxdKLAxU3r1YBHdoAIPIQM3oECHUQAA..i&w=750&h=937&hcb=2&ved=2ahUKEwjEztHtxdKLAxU3r1YBHdoAIPIQM3oECHUQAA",
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
