package com.modoohealing.modo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.bind
import androidx.databinding.DataBindingUtil.setContentView
import com.modoohealing.modo.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)

        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.btnKakaoLogin.setOnClickListener {
            Toast.makeText(mContext, "카톡로그인 눌림", Toast.LENGTH_SHORT).show()
        }

        binding.btnGoogleLogin.setOnClickListener {
            Toast.makeText(mContext, "구글로그인 눌림", Toast.LENGTH_SHORT).show()
        }

        binding.btnSignUp.setOnClickListener {
            Toast.makeText(mContext, "간편 회원가입 클릭", Toast.LENGTH_SHORT).show()
        }
        binding.btnNaverLogin.setOnClickListener {
            Toast.makeText(mContext, "네이버 로그인클릭", Toast.LENGTH_SHORT).show()
        }
        binding.btnEmailLogin.setOnClickListener {
            Toast.makeText(mContext, "이메일 로그인 클릭", Toast.LENGTH_SHORT).show()
        }
    }

    override fun setValues() {
    }
}