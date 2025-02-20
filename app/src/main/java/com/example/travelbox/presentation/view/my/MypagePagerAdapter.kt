package com.example.mypage

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.travelbox.presentation.view.my.DestinationsFragment
import com.example.travelbox.presentation.view.my.MypageTabFragment
import com.example.travelbox.presentation.view.my.SouvenirsFragment
import com.example.travelbox.presentation.view.my.TravelHistoryFragment
import com.example.travelbox.presentation.view.my.TravelStylingFragment

class MypagePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4 // 탭의 개수

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TravelHistoryFragment()
            1 -> SouvenirsFragment()
            2 -> DestinationsFragment()
            3 -> TravelStylingFragment()
            else -> throw IllegalStateException("Unexpected position: $position")
        }
    }
}

