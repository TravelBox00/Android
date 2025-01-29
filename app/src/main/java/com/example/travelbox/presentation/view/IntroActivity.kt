package com.example.travelbox.presentation.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.travelbox.R
import com.example.travelbox.databinding.ActivityIntroBinding
import com.example.travelbox.databinding.ActivityLoginBinding
import com.example.travelbox.presentation.view.user.LoginActivity
import com.example.travelbox.presentation.view.user.SignupActivity

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 로그인 버튼 클릭 리스너
        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.goResisterTV.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
}