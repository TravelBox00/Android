package com.example.travelbox.presentation.view.calendar

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.Fragment
import com.example.travelbox.R
import com.example.travelbox.databinding.FabMenuBinding


class FabMenuFragment : Fragment() {

    private lateinit var binding: FabMenuBinding
    private var isFabOpen = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FabMenuBinding.inflate(inflater, container, false)

        setupFabMenu()
        setupFabClickListeners()

        return binding.root
    }

    private fun setupFabMenu() {
        val fabMain = binding.fabMain
        val fabSchedule = binding.fabSchedule
        val fabPost = binding.fabPost

        fabMain.setOnClickListener {
            if (isFabOpen) {
                closeFabMenu(fabSchedule, fabPost, fabMain)
            } else {
                openFabMenu(fabSchedule, fabPost, fabMain)
            }
            isFabOpen = !isFabOpen
        }
    }

    private fun setupFabClickListeners() {
        binding.fabSchedule.setOnClickListener {
            Log.d("FabMenuFragment", "✅ fabSchedule 버튼 클릭됨!")
            println("스케쥴 버튼 클릭됨")
            val intent = Intent(requireContext(), ScheduleActivity::class.java)
            startActivity(intent)
        }


    }




    private fun openFabMenu(schedule: View, post: View, mainFab: View) {
        schedule.visibility = View.VISIBLE
        post.visibility = View.VISIBLE

        val rotateAnim = ObjectAnimator.ofFloat(mainFab, "rotation", 0f, 45f)
        rotateAnim.duration = 200
        rotateAnim.start()

        val animSchedule = ObjectAnimator.ofPropertyValuesHolder(
            schedule,
            PropertyValuesHolder.ofFloat("translationY", -180f),
            PropertyValuesHolder.ofFloat("translationX", -100f),
            PropertyValuesHolder.ofFloat("alpha", 1f)
        )
        animSchedule.duration = 300
        animSchedule.interpolator = AccelerateDecelerateInterpolator()
        animSchedule.start()

        val animPost = ObjectAnimator.ofPropertyValuesHolder(
            post,
            PropertyValuesHolder.ofFloat("translationY", -180f),
            PropertyValuesHolder.ofFloat("translationX", 100f),
            PropertyValuesHolder.ofFloat("alpha", 1f)
        )
        animPost.duration = 300
        animPost.interpolator = AccelerateDecelerateInterpolator()
        animPost.start()
    }

    private fun closeFabMenu(schedule: View, post: View, mainFab: View) {
        val rotateAnim = ObjectAnimator.ofFloat(mainFab, "rotation", 45f, 0f)
        rotateAnim.duration = 200
        rotateAnim.start()

        val animSchedule = ObjectAnimator.ofPropertyValuesHolder(
            schedule,
            PropertyValuesHolder.ofFloat("translationY", 0f),
            PropertyValuesHolder.ofFloat("translationX", 0f),
            PropertyValuesHolder.ofFloat("alpha", 0f)
        )
        animSchedule.duration = 300
        animSchedule.interpolator = AccelerateDecelerateInterpolator()
        animSchedule.start()

        val animPost = ObjectAnimator.ofPropertyValuesHolder(
            post,
            PropertyValuesHolder.ofFloat("translationY", 0f),
            PropertyValuesHolder.ofFloat("translationX", 0f),
            PropertyValuesHolder.ofFloat("alpha", 0f)
        )
        animPost.duration = 300
        animPost.interpolator = AccelerateDecelerateInterpolator()
        animPost.start()

        schedule.postDelayed({ schedule.visibility = View.GONE }, 300)
        post.postDelayed({ post.visibility = View.GONE }, 300)
    }
}
