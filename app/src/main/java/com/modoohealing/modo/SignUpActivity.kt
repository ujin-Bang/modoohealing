package com.modoohealing.modo

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.modoohealing.modo.databinding.ActivitySignUpBinding

class SignUpActivity : BaseActivity() {
    lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        btnBack.setOnClickListener {
            val alert = AlertDialog.Builder(mContext)
                .setTitle("회원가입을 종료하시겠습니까?")
                .setPositiveButton("종료", DialogInterface.OnClickListener { dialog, which ->
                    finish()
                })
                .setNegativeButton("아니요", DialogInterface.OnClickListener { dialog, which ->
                    return@OnClickListener
                })
                .create()
                alert.show()
        }

    }

    override fun setValues() {
        txtMainName.isVisible = false
        layoutSignup.isVisible = true


    }
}