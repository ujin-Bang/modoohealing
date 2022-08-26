package com.modoohealing.modo

import android.os.Bundle
import android.util.Patterns
import android.util.Patterns.EMAIL_ADDRESS
import android.widget.EditText
import androidx.core.util.PatternsCompat.EMAIL_ADDRESS
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.modoohealing.modo.databinding.ActivityEmailLoginBinding
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
            //todo 서버호출
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