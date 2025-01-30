package com.example.travelbox.presentation.view.post

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.travelbox.R
import com.example.travelbox.databinding.FragmentAddPostBinding
import com.google.android.flexbox.FlexboxLayout

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddRegion.setOnClickListener {
            val regionText = binding.etRegion.text.toString().trim()
            if (regionText.isNotEmpty()) {
                addRegionButton(regionText)
                binding.etRegion.text.clear()
            }
        }

        binding.btnAddHashtag.setOnClickListener {
            val hashtagText = binding.etHashtag.text.toString().trim()
            if (hashtagText.isNotEmpty()) {
                addHashtagButton(hashtagText)
                binding.etHashtag.text.clear()
            }
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

    private fun createButton(text: String): Button {
        return Button(requireContext()).apply {
            this.text = text
            setPadding(8, 2, 8, 2)
            setBackgroundResource(R.drawable.btn_create)
            layoutParams = FlexboxLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    35f,
                    resources.displayMetrics
                ).toInt()
            )
        }
    }

    private fun addRegionButton(region: String) {
        val button = createButton("#$region")
        binding.regionButtonContainer.addView(button)
    }

    private fun addHashtagButton(hashtag: String) {
        val button = createButton("#$hashtag")
        binding.hashtagButtonContainer.addView(button)
    }

}