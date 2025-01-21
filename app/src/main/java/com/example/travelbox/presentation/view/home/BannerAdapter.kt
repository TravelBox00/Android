package com.example.travelbox.presentation.view.home

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.travelbox.R

class BannerAdapter(fa: HomeFragment, private val count: Int) : FragmentStateAdapter(fa) {
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> Banner1Fragment()
            1 -> Banner2Fragment()
            else -> Banner3Fragment()
        }
    }

    override fun getItemCount(): Int = count


}