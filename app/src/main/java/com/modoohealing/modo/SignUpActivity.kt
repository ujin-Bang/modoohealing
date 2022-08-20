package com.modoohealing.modo

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.modoohealing.modo.databinding.ActivitySignUpBinding

class SignUpActivity : BaseActivity() {
    lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        btnBack.setOnClickListener {
            val alert = AlertDialog.Builder(mContext)
                .setTitle("회원가입을 종료하시겠습니까?")
                .setPositiveButton("종료", DialogInterface.OnClickListener { _, _ ->
                    finish()
                })
                .setNegativeButton("아니요", DialogInterface.OnClickListener { _, _ ->
                    return@OnClickListener
                })
                .create()
            alert.show()
        }

        spinnerEmail()
        spinnerBirthYear()
        spinnerArea()
        spinnerSpouseArea()
    }

    override fun setValues() {
        txtMainName.isVisible = false
        layoutSignup.isVisible = true


    }

    @SuppressLint("ResourceType")
    fun spinnerEmail() {

        var items = resources.getStringArray(R.array.email)

        var adapter = ArrayAdapter<String>(this, R.layout.signup_custom_item, items)
        binding.spinnerSignup.adapter = adapter

        binding.spinnerSignup.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(mContext, "선택아이템 :${position}", Toast.LENGTH_SHORT).show()

                when (position) {
                    0 -> {

                    }
                    1 -> {

                    }
                    2 -> {

                    }
                    3 -> {

                    }
                    else -> {
                        binding.spinnerSignup.isVisible = false
                        binding.edtDirectInput.isVisible = true

                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

                return
            }

        }
    }

    fun spinnerBirthYear() {

        var items = resources.getStringArray(R.array.birth_year)

        var adapter = ArrayAdapter<String>(mContext, R.layout.signup_custom_item, items)

        binding.spinnerBirthYear.adapter = adapter

        binding.spinnerBirthYear.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        0 -> {
                            return
                        }
                        in 1..10 -> {

                        }
                        in 11..20 -> {

                        }
                        in 21..30 -> {

                        }
                        in 31..40 -> {

                        }
                        else -> {

                        }
                    }

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }
    }

    fun spinnerArea(){
        var areaItems = resources.getStringArray(R.array.area)
        var adapter = ArrayAdapter<String>(mContext,R.layout.signup_custom_item, areaItems)
        binding.spinnerArea.adapter = adapter

        binding.spinnerArea.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

    }

    fun spinnerSpouseArea(){
        val items = resources.getStringArray(R.array.spouseArea)
        var adapter = ArrayAdapter<String>(mContext,R.layout.signup_custom_item, items)
        binding.spinnerSpouseArea.adapter = adapter

        binding.spinnerSpouseArea.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
    }
}

