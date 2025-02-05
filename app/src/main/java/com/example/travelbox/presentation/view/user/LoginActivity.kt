package com.example.travelbox.presentation.view.user

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.travelbox.R
import com.example.travelbox.data.repository.auth.AuthRepository
import com.example.travelbox.databinding.ActivityLoginBinding
import com.example.travelbox.presentation.view.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var passwordET: EditText
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewPasswordIv: ImageView
    private lateinit var hidePasswordIv: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View Binding 초기화
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        passwordET = findViewById(R.id.pwd_ET)
        viewPasswordIv = findViewById(R.id.show_pwd_IV)
        hidePasswordIv = findViewById(R.id.hide_pwd_IV)

        // 비밀번호 보기/숨기기 기능 설정
        hidePasswordIv.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> { // 버튼을 누르면 비밀번호 표시
                    passwordET.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    hidePasswordIv.visibility = ImageView.GONE
                    viewPasswordIv.visibility = ImageView.VISIBLE
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> { // 버튼을 뗐을 때 비밀번호 숨김
                    passwordET.transformationMethod = PasswordTransformationMethod.getInstance()
                    viewPasswordIv.visibility = ImageView.GONE
                    hidePasswordIv.visibility = ImageView.VISIBLE
                }
            }
            true
        }

//        // 아이디 찾기 텍스트뷰 클릭 리스너
//        binding.textView.setOnClickListener {
//            startActivity(Intent(this, SearchmainActivity::class.java))
//        }

        // 로그인 버튼 클릭 리스너
        binding.loginBtn.setOnClickListener {
            val id = binding.idET.text.toString()
            val password = binding.pwdET.text.toString()

            if (id.isNotEmpty() && password.isNotEmpty()) {
                //startActivity(Intent(this, MainActivity::class.java))

                // 로그인 api 호출
                login(id, password)
            } else {
                // 로그인 실패 예시
                Toast.makeText(this, "로그인 실패: 아이디와 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }



    // 로그인
    private fun login(id: String, password: String) {
        AuthRepository.login(id, password) { isSuccess ->
            runOnUiThread {
                if (isSuccess) {
                    Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "로그인 실패: 아이디 또는 비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}