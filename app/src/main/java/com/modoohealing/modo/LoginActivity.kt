package com.modoohealing.modo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.bind
import androidx.databinding.DataBindingUtil.setContentView
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.modoohealing.modo.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        val keyHash = Utility.getKeyHash(this)//카카오SDK를 이용한 KeyHash값 받기
        Log.d("Hash", keyHash)

        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.btnKakaoLogin.setOnClickListener {

            //카톡 앱이 깔려 있는 상황
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(mContext)) {
                UserApiClient.instance.loginWithKakaoTalk(mContext) { token, error ->

                    if (error != null) {
                        Log.e("카톡로그인", "로그인 실패")
                    } else if (token != null) {
                        Log.d("카톡로그인", "로그인 성공")
                        Log.d("카톡로그인", token.accessToken)

                    }
                }

            } else { //카톡 앱이 깔려있지 않는 상황.
                UserApiClient.instance.loginWithKakaoAccount(mContext) { token, error ->

                    if (error != null) {
                        Log.e("카톡로그인", "로그인 실패")
                        Log.e("카톡로그인", error.message.toString())
                    } else if (token != null) {
                        Log.d("카톡로그인", "로그인 성공")
                        Log.d("카톡로그인", token.accessToken)

                    }
                }
            }
        }
    }


            override fun setValues() {

            }
}

