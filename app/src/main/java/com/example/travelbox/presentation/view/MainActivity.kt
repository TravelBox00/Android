package com.example.travelbox.presentation.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.travelbox.R
import com.example.travelbox.databinding.ActivityMainBinding
import com.example.travelbox.presentation.view.calendar.CalendarFragment
import com.example.travelbox.presentation.view.home.HomeFragment
import com.example.travelbox.presentation.view.my.MypageFragment
import com.example.travelbox.presentation.view.post.PostFragment
import com.example.travelbox.presentation.view.search.SearchFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding :ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 초기 화면 설정
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commit()

        binding.bottomNavi.selectedItemId = R.id.homeFragment

        initBottomNavigation()

    }



    // BottomNavigation 함수
    private fun initBottomNavigation() {
        binding.bottomNavi.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.calendarFragment -> {
                    loadFragment(CalendarFragment())
                    true
                }
                R.id.postFragment -> {
                    loadFragment(PostFragment())
                    true
                }
                R.id.searchFragment -> {
                    loadFragment(SearchFragment())
                    true
                }
                R.id.myFragment -> {
                    loadFragment(MypageFragment())
                    true
                }
                else -> false
            }
        }
    }

    // Fragment 교체 함수
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, fragment)
            .commit()
    }

}