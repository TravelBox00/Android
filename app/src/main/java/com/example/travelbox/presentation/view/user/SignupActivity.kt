package com.example.travelbox.presentation.view.user

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.travelbox.R
import com.example.travelbox.data.repository.auth.AuthRepository
import com.example.travelbox.databinding.ActivitySignupBinding
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var passwordET: EditText
    private lateinit var confirmPasswordET: EditText
    private lateinit var pwdUnmatchTV: TextView
    private var isIdAvailable: Boolean = false // ID 사용 가능 여부 저장

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View Binding 초기화
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtnIV.setOnClickListener {
            onBackPressedDispatcher.onBackPressed() // 뒤로 가기 기능 실행
        }

        passwordET = binding.pwdET
        confirmPasswordET = binding.pwdCheckET
        pwdUnmatchTV = binding.pwdUnmatchTV

        binding.duplicationBtn.setOnClickListener {
            val userTag = binding.idET.text.toString().trim() // 공백 제거

            if (userTag.isEmpty()) {
                Toast.makeText(this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.d("SignupActivity", "중복 확인 요청 ID: $userTag") // 입력한 ID 로그 출력

            AuthRepository.duplicate(userTag) { isAvailable ->
                runOnUiThread {
                    Log.d("SignupActivity", "중복 확인 결과: $isAvailable") // 서버 응답 로그

                    if (!isAvailable) {
                        binding.idavailable.visibility = View.VISIBLE
                        binding.idUnavailable.visibility = View.GONE
                        isIdAvailable = true
                    } else {
                        binding.idUnavailable.visibility = View.VISIBLE
                        binding.idavailable.visibility = View.GONE
                        isIdAvailable = false
                    }
                }
            }
        }


        binding.idET.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.idavailable.visibility = View.GONE
                binding.idUnavailable.visibility = View.GONE
                isIdAvailable = false // 새로운 값 입력 시 다시 확인 필요
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

//        binding.signupBtn.setOnClickListener {
//            val name = binding.nameET.text.toString()
//            val id = binding.idET.text.toString()
//            val password = binding.pwdET.text.toString()
//            val confirmPassword = binding.pwdCheckET.text.toString()
//
//            if (name.isNotEmpty() && id.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
//                if (password == confirmPassword) {
//                    AuthRepository.signUp(id, password, name) { isSuccess ->
//                        runOnUiThread {
//                            if (isSuccess) {
//                                startActivity(Intent(this, SignUpSuccessActivity::class.java))
//                            } else {
//                                Toast.makeText(this, "회원가입 실패. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
//                            }
//                        }
//                    }
//                } else {
//                    Toast.makeText(this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
//                }
//            } else {
//                Toast.makeText(this, "모든 항목을 입력하세요", Toast.LENGTH_SHORT).show()
//            }
//        }


        // 회원가입 버튼 클릭 이벤트
        binding.signupBtn.setOnClickListener {
            val name = binding.nameET.text.toString()
            val id = binding.idET.text.toString()
            val password = binding.pwdET.text.toString()
            val confirmPassword = binding.pwdCheckET.text.toString()

            // 모든 입력 필드가 채워졌는지 확인
            if (name.isEmpty() || id.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "모든 항목을 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ID 중복 확인 여부 체크
            if (!isIdAvailable) {
                Toast.makeText(this, "ID 중복확인을 해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 비밀번호 길이 체크
            if (password.length < 6 || password.length > 10) {
                Toast.makeText(this, "비밀번호는 6~10자리로 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 비밀번호 일치 여부 체크
            if (password != confirmPassword) {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 회원가입 진행
            AuthRepository.signUp(id, password, name) { isSuccess ->
                runOnUiThread {
                    if (isSuccess) {
                        startActivity(Intent(this, SignUpSuccessActivity::class.java))
                    } else {
                        Toast.makeText(this, "회원가입 실패. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }



        // 비밀번호 보기/숨기기 기능 설정
        setupPasswordToggle(passwordET, binding.showPwdIV, binding.hidePwdIV)
        setupPasswordToggle(confirmPasswordET, binding.showPwdIV2, binding.hidePwdIV2)

        // 비밀번호 일치 여부 확인
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val password = passwordET.text.toString()
                val confirmPassword = confirmPasswordET.text.toString()

                if (password.isEmpty() || confirmPassword.isEmpty()) {
                    pwdUnmatchTV.visibility = View.GONE
                } else if (password == confirmPassword) {
                    pwdUnmatchTV.visibility = View.GONE
                } else {
                    pwdUnmatchTV.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        passwordET.addTextChangedListener(textWatcher)
        confirmPasswordET.addTextChangedListener(textWatcher)
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