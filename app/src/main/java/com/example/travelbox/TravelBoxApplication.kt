package com.example.travelbox

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.travelbox.data.network.ApiNetwork

class TravelBoxApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ApiNetwork.init(this)  // SharedPreferences 초기화
    }
}