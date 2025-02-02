package com.example.travelbox.presentation.view.post

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintSet
import com.example.travelbox.R
import com.example.travelbox.databinding.FragmentAddPostBinding
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddPostFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddPostFragment : Fragment() {

    lateinit var binding: FragmentAddPostBinding

    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>

    private lateinit var regionAdapter: ButtonAdapter
    private lateinit var hashtagAdapter: ButtonAdapter

    private val regionList = mutableListOf<String>()
    private val hashtagList = mutableListOf<String>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri: Uri? = result.data?.data
                imageUri?.let {
                    binding.ivPhoto2.setImageURI(it)
                    binding.ivPhoto2.visibility = View.VISIBLE

                    val constraintSet = ConstraintSet()
                    constraintSet.clone(binding.photoContainer)

                    constraintSet.clear(binding.ivPhoto1.id, ConstraintSet.END)
                    constraintSet.clear(binding.ivPhoto1.id, ConstraintSet.START)
                    constraintSet.clear(binding.ivPhoto2.id, ConstraintSet.END)
                    constraintSet.clear(binding.ivPhoto2.id, ConstraintSet.START)

                    constraintSet.connect(binding.ivPhoto1.id, ConstraintSet.END, binding.ivPhoto2.id, ConstraintSet.START, 4)
                    constraintSet.connect(binding.ivPhoto2.id, ConstraintSet.START, binding.ivPhoto1.id, ConstraintSet.END, 4)

                    constraintSet.connect(binding.ivPhoto1.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
                    constraintSet.connect(binding.ivPhoto2.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)

                    constraintSet.applyTo(binding.photoContainer)
                }
            }
        }

        binding.ivPhoto1.setOnClickListener {
            pickImageFromGallery()
        }


        val categoryButtons = listOf(
            binding.btnCategory1,
            binding.btnCategory2,
            binding.btnCategory3,
            binding.btnCategory4
        )
        categoryButtons.forEach { button ->
            button.setOnClickListener {
                val clicked = updateButtonState(button, button.tag as? Boolean ?: false)
                button.tag = clicked
            }
        }


        val regionLayoutManager = FlexboxLayoutManager(requireContext()).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
        }
        binding.recyclerViewRegions.layoutManager = regionLayoutManager

        val hashtagLayoutManager = FlexboxLayoutManager(requireContext()).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
        }
        binding.recyclerViewHashtags.layoutManager = hashtagLayoutManager


        regionAdapter = ButtonAdapter(regionList)
        binding.recyclerViewRegions.adapter = regionAdapter

        hashtagAdapter = ButtonAdapter(hashtagList)
        binding.recyclerViewHashtags.adapter = hashtagAdapter


        binding.btnAddRegion.setOnClickListener {
            val regionText = binding.etRegion.text.toString().trim()
            if (regionText.isNotEmpty() && !regionList.contains(regionText)) {
                regionList.add(regionText)
                regionAdapter.notifyItemInserted(regionList.size - 1)
                binding.etRegion.text.clear()
            }
        }

        binding.btnAddHashtag.setOnClickListener {
            val hashtagText = binding.etHashtag.text.toString().trim()
            if (hashtagText.isNotEmpty() && !hashtagList.contains(hashtagText)) {
                hashtagList.add("#$hashtagText")
                hashtagAdapter.notifyItemInserted(hashtagList.size - 1)
                binding.etHashtag.text.clear()
            }
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        imagePickerLauncher.launch(intent)
    }

    private fun updateButtonState(button: Button, clicked: Boolean): Boolean {
        if (clicked) {
            button.setTextColor(Color.parseColor("#ACACAC"))
            button.setBackgroundResource(R.drawable.btn_category)
        } else {
            button.setTextColor(Color.parseColor("#007151"))
            button.setBackgroundResource(R.drawable.btn_create)
        }
        return !clicked
    }

}