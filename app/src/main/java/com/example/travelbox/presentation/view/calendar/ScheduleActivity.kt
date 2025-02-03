package com.example.travelbox.presentation.view.calendar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.travelbox.R
import com.example.travelbox.presentation.view.calendar.ScheduleFragment

class ScheduleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.schedule_container, ScheduleFragment())
                .commit()
        }
    }
}
