package com.example.travelbox.presentation.view.calendar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.travelbox.R

class ScheduleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        val selectedDate = intent.getStringExtra("selected_date") ?: ""

        if (savedInstanceState == null) {
            val fragment = ScheduleFragment().apply {
                arguments = Bundle().apply {
                    putString("selected_date", selectedDate)
                }
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.schedule_container, fragment)
                .commit()
        }

    }
}
