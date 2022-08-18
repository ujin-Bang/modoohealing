package com.modoohealing.modo

import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.modoohealing.modo.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginActivity : BaseActivity() {

    lateinit var binding: ActivityLoginBinding
    private val TAG = this.javaClass.simpleName
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        val keyHash = Utility.getKeyHash(this)//카카오SDK를 이용한 KeyHash값 받기
        Log.d("Hash", keyHash)
        setValues()
        setupEvents()

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

                        getMyInfoFromKakao()

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

                        getMyInfoFromKakao()
                    }
                }
            }
        }

            binding.btnGoogleLogin.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build()
                    val googleSignInClient = GoogleSignIn.getClient(mContext,gso)
                    val signInIntent: Intent = googleSignInClient.signInIntent
                    launcher.launch(signInIntent)
                }
            }

    }

    override fun setValues() {

        launcher()
    }

    fun getMyInfoFromKakao() {

        UserApiClient.instance.me { user, error ->

            if (error != null) {
                Log.d("카톡로그인", "사용자 정보 요청 실패", error)
            } else if (user != null) {
                Log.d(
                    "카톡로그인", "사용자 정보요청 성공" +
                            "\n 회원번호: ${user.id}" +
                            "\n 이메일: ${user.kakaoAccount?.email}" +
                            "\n 닉네임: ${user.kakaoAccount?.profile?.nickname}"
                )
            }
        }
    }

    fun launcher(){
        firebaseAuth = FirebaseAuth.getInstance()
        launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(), ActivityResultCallback { result ->
                Log.e(TAG, "resultCode : ${result.resultCode}")
                Log.e(TAG, "result : $result")
                if (result.resultCode == RESULT_OK) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    try {
                        task.getResult(ApiException::class.java)?.let { account ->
                            val tokenId = account.idToken
                            if (tokenId != null && tokenId != "") {
                                val credential: AuthCredential = GoogleAuthProvider.getCredential(account.idToken, null)
                                firebaseAuth.signInWithCredential(credential)
                                    .addOnCompleteListener {
                                        if (firebaseAuth.currentUser != null) {
                                            val user: FirebaseUser = firebaseAuth.currentUser!!
                                            val email = user.email.toString()
                                            Log.e(TAG, "email : $email")
                                            val googleSignInToken = account.idToken ?: ""
                                            if (googleSignInToken != "") {
                                                Log.e(TAG, "googleSignInToken : $googleSignInToken")
                                            } else {
                                                Log.e(TAG, "googleSignInToken이 null")
                                            }
                                        }
                                    }
                            }
                        } ?: throw Exception()
                    }   catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            })

    }
}

