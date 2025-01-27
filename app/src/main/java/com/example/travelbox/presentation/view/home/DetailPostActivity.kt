package com.example.travelbox.presentation.view.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.travelbox.R
import com.example.travelbox.databinding.ActivityDetailPostBinding

class DetailPostActivity : AppCompatActivity() {


    lateinit var binding: ActivityDetailPostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 인텐트로부터 데이터 수신
        val imageResId = intent.getIntExtra("image", R.drawable.post_ex1)  // 기본값 설정
        val id = intent.getStringExtra("id") ?: "No Id"
        val title = intent.getStringExtra("title") ?: "No Title"

        // 데이터 설정
        binding.detailImage.setImageResource(imageResId)
        binding.detailId.text = id
        binding.detailTitle.text = title

    }
}