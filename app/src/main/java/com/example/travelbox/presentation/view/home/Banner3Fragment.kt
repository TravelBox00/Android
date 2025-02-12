package com.example.travelbox.presentation.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.travelbox.R
import com.example.travelbox.databinding.FragmentBanner2Binding
import com.example.travelbox.databinding.FragmentBanner3Binding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Banner3Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Banner3Fragment : Fragment() {
    lateinit var binding: FragmentBanner3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBanner3Binding.inflate(inflater, container, false)


        // 배너 텍스트

        val tvTitle = binding.tvTitle

        val tvDescription = binding.tvDescription


        tvTitle.text = "교토"
        tvDescription.text = "고요한 전통과 현대의 조화"


        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Banner3Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Banner3Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}