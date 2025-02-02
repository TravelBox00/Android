package com.example.travelbox.presentation.view.user

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.travelbox.R
import com.example.travelbox.databinding.ActivityIntroBinding
import com.example.travelbox.databinding.ActivitySignUpSuccessBinding

class SignUpSuccessActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 로그인 버튼 클릭 리스너
        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}