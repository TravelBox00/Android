package com.example.travelbox.presentation.view.user

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.travelbox.R
import com.example.travelbox.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var passwordET: EditText
    private lateinit var confirmPasswordET: EditText
    private lateinit var pwdUnmatchTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View Binding 초기화
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        passwordET = binding.pwdET
        confirmPasswordET = binding.pwdCheckET
        pwdUnmatchTV = binding.pwdUnmatchTV

        binding.duplicationBtn.setOnClickListener {
            binding.idavailable.visibility = TextView.VISIBLE
        }

        // 비밀번호 보기/숨기기 기능 설정
        setupPasswordToggle(passwordET, binding.showPwdIV, binding.hidePwdIV)
        setupPasswordToggle(confirmPasswordET, binding.showPwdIV2, binding.hidePwdIV2)

        // 비밀번호 일치 여부 확인
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val password = passwordET.text.toString()
                val confirmPassword = confirmPasswordET.text.toString()

                if (password == confirmPassword) {
                    pwdUnmatchTV.visibility = TextView.GONE
                } else {
                    pwdUnmatchTV.visibility = TextView.VISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        passwordET.addTextChangedListener(textWatcher)
        confirmPasswordET.addTextChangedListener(textWatcher)

        // 회원가입 버튼 클릭 리스너
        binding.signupBtn.setOnClickListener {
            val name = binding.nameET.text.toString()
            val id = binding.idET.text.toString()
            val password = binding.pwdET.text.toString()
            val confirmPassword = binding.pwdCheckET.text.toString()

            if (name.isNotEmpty() && id.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    // 회원가입 성공 예시
                    startActivity(Intent(this, SignUpSuccessActivity::class.java))
                } else {
                    Toast.makeText(this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "모든 필드를 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupPasswordToggle(editText: EditText, showIcon: ImageView, hideIcon: ImageView) {
        hideIcon.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> { // 버튼을 누르면
                    editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    hideIcon.visibility = ImageView.GONE
                    showIcon.visibility = ImageView.VISIBLE
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> { // 버튼을 뗐을 때
                    editText.transformationMethod = PasswordTransformationMethod.getInstance()
                    showIcon.visibility = ImageView.GONE
                    hideIcon.visibility = ImageView.VISIBLE
                }
            }
            true
        }

        showIcon.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> { // 버튼을 누르면
                    editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    showIcon.visibility = ImageView.GONE
                    hideIcon.visibility = ImageView.VISIBLE
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> { // 버튼을 뗐을 때
                    editText.transformationMethod = PasswordTransformationMethod.getInstance()
                    hideIcon.visibility = ImageView.GONE
                    showIcon.visibility = ImageView.VISIBLE
                }
            }
            true
        }
    }
}