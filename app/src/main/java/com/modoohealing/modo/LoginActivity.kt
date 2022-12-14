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
import com.google.android.gms.common.api.Response
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.modoohealing.modo.databinding.ActivityLoginBinding
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.api.NidProfileApi
import com.navercorp.nid.profile.data.NidProfileResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class LoginActivity : BaseActivity() {

    lateinit var binding: ActivityLoginBinding
    private val TAG = this.javaClass.simpleName
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        setValues()
        setupEvents()

    }

    override fun setupEvents() {

        binding.btnEmailLogin.setOnClickListener {
            startActivity(Intent(mContext, EmailLoginActivity::class.java))
        }

        binding.btnKakaoLogin.setOnClickListener {

           // val keyHash = Utility.getKeyHash(this)//?????????SDK??? ????????? KeyHash??? ??????
           // Log.d("Hash", keyHash)

            //?????? ?????? ?????? ?????? ??????
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(mContext)) {
                UserApiClient.instance.loginWithKakaoTalk(mContext) { token, error ->

                    if (error != null) {
                        Log.e("???????????????", "????????? ??????")
                    } else if (token != null) {
                        Log.d("???????????????", "????????? ??????")
                        Log.d("???????????????", token.accessToken)

                        getMyInfoFromKakao()
                        startActivity(Intent(mContext,MainActivity::class.java))

                    }
                }

            } else { //?????? ?????? ???????????? ?????? ??????.
                UserApiClient.instance.loginWithKakaoAccount(mContext) { token, error ->

                    if (error != null) {
                        Log.e("???????????????", "????????? ??????")
                        Log.e("???????????????", error.message.toString())
                    } else if (token != null) {
                        Log.d("???????????????", "????????? ??????")
                        Log.d("???????????????", token.accessToken)

                        getMyInfoFromKakao()
                        startActivity(Intent(mContext,MainActivity::class.java))
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
                val googleSignInClient = GoogleSignIn.getClient(mContext, gso)
                val signInIntent: Intent = googleSignInClient.signInIntent
                launcher.launch(signInIntent)
            }
        }

        binding.btnNaverLogin.setOnClickListener {
            val oAuthLoginCallback = object : OAuthLoginCallback {
                override fun onSuccess() {
                    // ????????? ????????? API ?????? ?????? ??? ?????? ????????? ????????????
                    NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
                        override fun onSuccess(result: NidProfileResponse) {
                            val name = result.profile?.name.toString()
                            val email = result.profile?.email.toString()
                            val gender = result.profile?.gender.toString()
                            val nickname = result.profile?.nickname.toString()
                            val id = result.profile?.id.toString()
                            //????????? API?????? ????????? ????????? ?????????. naver????????????????????? ??????.
                            Log.e(TAG, "????????? ???????????? ?????? ?????? - ?????? : $name")
                            Log.e(TAG, "????????? ???????????? ?????? ?????? - ????????? : $email")
                            Log.e(TAG, "????????? ???????????? ?????? ?????? - ?????? : $gender")
                            Log.e(TAG, "????????? ???????????? ?????? ?????? - ?????????: $nickname")
                            Log.e(TAG, "????????? ???????????? ?????? ?????? - ID: $id")

                            val naverAccessToken = NaverIdLoginSDK.getAccessToken()
                            Log.e(TAG, "naverAccessToken : $naverAccessToken")

                            startActivity(Intent(mContext,MainActivity::class.java))

                        }

                        override fun onError(errorCode: Int, message: String) {
                            //
                        }

                        override fun onFailure(httpStatus: Int, message: String) {
                            //
                        }
                    })
                }

                override fun onError(errorCode: Int, message: String) {
                    val naverAccessToken = NaverIdLoginSDK.getAccessToken()
                    Log.e(TAG, "naverAccessToken : $naverAccessToken")
                }

                override fun onFailure(httpStatus: Int, message: String) {
                    //
                }
            }

            val clientId = getString(R.string.naver_client_id)
            val clientPw = getString(R.string.naver_client_secret)
            val appName = getString(R.string.naver_app_name)
            NaverIdLoginSDK.initialize(mContext, clientId, clientPw,appName)
            NaverIdLoginSDK.authenticate(mContext, oAuthLoginCallback)

        }

        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(mContext,SignUpActivity::class.java))
        }
    }



    override fun setValues() {

        launcherGoogleFirebase()

    }

    fun getMyInfoFromKakao() {

        UserApiClient.instance.me { user, error ->

            if (error != null) {
                Log.d("???????????????", "????????? ?????? ?????? ??????", error)
            } else if (user != null) {
                Log.d(
                    "???????????????", "????????? ???????????? ??????" +
                            "\n ????????????: ${user.id}" +
                            "\n ?????????: ${user.kakaoAccount?.email}" +
                            "\n ?????????: ${user.kakaoAccount?.profile?.nickname}"
                )
            }
        }
    }

    fun launcherGoogleFirebase() {
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
                                val credential: AuthCredential =
                                    GoogleAuthProvider.getCredential(account.idToken, null)
                                firebaseAuth.signInWithCredential(credential)
                                    .addOnCompleteListener {
                                        if (firebaseAuth.currentUser != null) {
                                            val user: FirebaseUser = firebaseAuth.currentUser!!
                                            val email = user.email.toString()
                                            val uId = user.uid.toString()
                                            val name = user.displayName.toString()
                                            Log.e(TAG, "email : $email")
                                            Log.e(TAG, "uId : $uId")
                                            Log.e(TAG, "name : $name")

                                            startActivity(Intent(mContext,MainActivity::class.java))


                                            val googleSignInToken = account.idToken ?: ""
                                            if (googleSignInToken != "") {
                                                Log.e(TAG, "googleSignInToken : $googleSignInToken")
                                            } else {
                                                Log.e(TAG, "googleSignInToken??? null")
                                            }
                                        }
                                    }
                            }
                        } ?: throw Exception()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            })

    }

 }


