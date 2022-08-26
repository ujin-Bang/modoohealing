package com.modoohealing.modo

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.util.Patterns.EMAIL_ADDRESS
import android.widget.EditText
import android.widget.Toast
import androidx.core.util.PatternsCompat.EMAIL_ADDRESS
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.modoohealing.modo.databinding.ActivityEmailLoginBinding
import com.modoohealing.modo.datamodel.BasicResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class EmailLoginActivity : BaseActivity() {

    lateinit var binding: ActivityEmailLoginBinding
    private var email = ""
    private var pw = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_email_login)

        setValues()
        setupEvents()

    }

    override fun setupEvents() {
        binding.btnLoginConfirm.setOnClickListener {
            email = binding.edtEmail.text.toString()
            pw = binding.edtPw.text.toString()

            //email이 이메일 형식이 아닌지 또는 값이 비어있다면
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()) {
                binding.txtEmailErrorMSG.isVisible = true
                binding.edtEmail.requestFocus()
                return@setOnClickListener
            }
            //비밀번호가 영어와 숫자로 조합된 6~12글자사이가 아니라면
            if (!Pattern.matches("^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{6,12}$", pw)) {
                binding.txtPwErrorMSG.isVisible = true
                binding.edtPw.requestFocus()
                return@setOnClickListener
            }
            //로그인 서버호출
            apiService.postRequestLogin(email,pw).enqueue(object : Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful){
                        val basicRespones = response.body()
                        if (basicRespones != null){
                            Log.d("코드값 + 메시지","코드값: ${basicRespones.code}, 메시지: ${basicRespones.message}")
                            Toast.makeText(mContext, "코드값: ${basicRespones.code}, 메시지: ${basicRespones.message}", Toast.LENGTH_SHORT).show()
                        }

                    else{
                        val errorJson= JSONObject(response.errorBody()!!.string())
                        Log.d("에러인 경우", errorJson.toString())

                        val message = errorJson.getString("message")
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                    }
                }


                  }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    Log.d("서버접속실패 :", t.toString())
                }

            })
        }


    }

    override fun setValues() {
        emilChange()
        pwChange()
    }

    //에디트 텍스트 이메일 변경시 실행
    fun emilChange() {
        binding.edtEmail.addTextChangedListener {
            val emailResult = it.toString()
            binding.txtEmailErrorMSG.isVisible = !Patterns.EMAIL_ADDRESS.matcher(emailResult).matches() || emailResult.isEmpty()

        }
        return

    }
    //에디트 텍스트 비밀번호가 변경시 실행
    fun pwChange(){
        binding.edtPw.addTextChangedListener {
            binding.txtPwErrorMSG.isVisible =
                !Pattern.matches("^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{6,12}$", it.toString())
        }
        return
    }
}