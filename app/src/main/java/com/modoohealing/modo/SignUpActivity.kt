package com.modoohealing.modo

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
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
    private var genderClicked = false
    private var emailChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }

    @SuppressLint("ClickableViewAccessibility", "ResourceAsColor")
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
        binding.btnSignUp.setOnClickListener {
            validation()
        }
        binding.imgMan.setOnClickListener {
            genderClicked = true
            binding.imgMan.alpha = 1F
            binding.imgWoman.alpha = 0.4F
            binding.txtGender.text = "성별"
        }
        binding.imgWoman.setOnClickListener {
            genderClicked = true
            binding.imgWoman.alpha = 1F
            binding.imgMan.alpha = 0.4F
            binding.txtGender.text = "성별"
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

    fun spinnerArea() {
        var areaItems = resources.getStringArray(R.array.area)
        var adapter = ArrayAdapter<String>(mContext, R.layout.signup_custom_item, areaItems)
        binding.spinnerArea.adapter = adapter

        binding.spinnerArea.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

    fun spinnerSpouseArea() {
        val items = resources.getStringArray(R.array.spouseArea)
        var adapter = ArrayAdapter<String>(mContext, R.layout.signup_custom_item, items)
        binding.spinnerSpouseArea.adapter = adapter

        binding.spinnerSpouseArea.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
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

    @SuppressLint("ResourceAsColor")
    fun validation() { //유효성 검사

        val email = binding.edtEmail.text.toString()
        val pw = binding.edtPw.text.toString()
        val rePw = binding.edtRePw.text.toString()
        val nickname = binding.edtNickname.toString()
        val selectedEmailCompany = binding.spinnerSignup.selectedItem.toString()
        val selectedBirthYear = binding.spinnerBirthYear.selectedItem.toString()
        val selectedMyArea = binding.spinnerArea.selectedItem.toString()
        val selectedSpouseArea = binding.spinnerSpouseArea.selectedItem.toString()
        val checkedTermsOfUse = binding.signupCheckbox.isChecked

        if (email.isEmpty()) {
            binding.edtEmail.requestFocus()
            Toast.makeText(mContext, "이메일 주소를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if(selectedEmailCompany.contains("직접") && binding.edtDirectInput.length() == 0 ){
            binding.edtDirectInput.requestFocus()
            Toast.makeText(mContext, "이메일 주소를 선택하세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if (binding.edtPw.length() !in 4..12) {
            binding.edtPw.requestFocus() //포커스 요청
            Toast.makeText(mContext, "4~12자 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if (rePw != pw) {
            binding.edtRePw.requestFocus()
            Toast.makeText(mContext, "비밀번호가 같지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        if (binding.edtNickname.length() !in 2..8) {
            binding.edtNickname.requestFocus()
            Toast.makeText(mContext, "2~8자 대화명을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if (!genderClicked) {
            binding.txtGender.text = "성별을 선택하세요."
            binding.imgMan.requestFocus()
            Toast.makeText(mContext, "성별을 선택해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if(selectedBirthYear.contains("태어난")){
            binding.spinnerBirthYear.requestFocus()
            Toast.makeText(mContext, "태어난 연도를 선택해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if (selectedMyArea.contains("현재")){
            binding.spinnerArea.requestFocus()
            Toast.makeText(mContext, "현재 거주지역을 선택해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if (selectedSpouseArea.contains("배우자")){
            binding.spinnerSpouseArea.requestFocus()
            Toast.makeText(mContext, "배우자 희망지역을 선택해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if (!checkedTermsOfUse){
            binding.signupCheckbox.requestFocus()
            Toast.makeText(mContext, "이용약관에 동의해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

    }
    
}
    



