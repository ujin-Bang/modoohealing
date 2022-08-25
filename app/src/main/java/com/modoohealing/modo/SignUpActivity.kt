package com.modoohealing.modo

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.modoohealing.modo.databinding.ActivitySignUpBinding
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding
    private var genderClicked = false
    private var selectedBirthYear =""
    private var selectedMyArea= ""
    private var selectedSpouseArea = ""
    lateinit var mContext: Context

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        mContext = this
        setupEvents()
        setValues()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ClickableViewAccessibility", "ResourceAsColor")
    fun setupEvents() {

        binding.btnBack.setOnClickListener {
            AlertDialog.Builder(mContext)
                .setTitle("회원가입을 종료하시겠습니까?")
                .setPositiveButton("종료", DialogInterface.OnClickListener { _, _ ->
                    finish()
                })
                .setNegativeButton("아니요", DialogInterface.OnClickListener { _, _ ->
                    return@OnClickListener
                })
                .create()
            .show()
        }
        binding.btnSignUp.setOnClickListener {
            validation()
        }
        binding.imgMan.setOnClickListener {
            genderClicked = true
            binding.imgMan.alpha = 1F
            binding.imgWoman.alpha = 0.4F
            binding.txtGender.text = "성별"
            binding.edtNickname.clearFocus()
            binding.txtMan.setTextColor(ContextCompat.getColor(applicationContext!!,R.color.blue))
            binding.txtWoman.setTextColor(ContextCompat.getColor(applicationContext!!,R.color.black))
            binding.txtGender.setTextColor(ContextCompat.getColor(applicationContext!!,R.color.black))//텍스트뷰 텍스트색 변경

        }
        binding.imgWoman.setOnClickListener {
            genderClicked = true
            binding.imgWoman.alpha = 1F
            binding.imgMan.alpha = 0.4F
            binding.txtGender.text = "성별"
            binding.edtNickname.clearFocus()
            binding.txtWoman.setTextColor(ContextCompat.getColor(applicationContext!!,R.color.error))
            binding.txtMan.setTextColor(ContextCompat.getColor(applicationContext!!,R.color.black))
            binding.txtGender.setTextColor(ContextCompat.getColor(applicationContext!!,R.color.black))//텍스트뷰 텍스트색변경
        }


        spinnerEmail()
        spinnerBirthYear()
        spinnerArea()
        spinnerSpouseArea()
    }

    fun setValues() {


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

                            binding.txtBirthYear.text = "태어난 연도"
                            binding.txtBirthYear.setTextColor(ContextCompat.getColor(applicationContext!!,R.color.black))
                            binding.txtSpinnerError.visibility = View.GONE
                            binding.edtNickname.clearFocus()
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

                when(position){
                    0 ->{

                    }
                    in 1..10 -> {
                        binding.txtMyArea.text = "나의 지역"
                        binding.txtMyArea.setTextColor(ContextCompat.getColor(applicationContext!!,R.color.black))
                        binding.txtSpinnerErrorArea.isVisible = false
                    }
                }
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

                    when(position){
                        in 1..10 ->{
                            binding.txtSpinnerErrorSpouseArea.visibility = View.GONE
                            binding.txtSpouseArea.text = "배우자 희망지역"
                            binding.txtSpouseArea.setTextColor(ContextCompat.getColor(applicationContext!!,R.color.black))
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceAsColor")
    fun validation() { //유효성 검사
        val email = binding.edtEmail.text.toString()
        val pw = binding.edtPw.text.toString()
        val rePw = binding.edtRePw.text.toString()
        val nickname = binding.edtNickname.text.toString()
        val edtEmailCompany = binding.edtDirectInput.text.toString()
        val selectedEmailCompany = binding.spinnerSignup.selectedItem.toString()
         selectedBirthYear = binding.spinnerBirthYear.selectedItem.toString()
         selectedMyArea = binding.spinnerArea.selectedItem.toString()
         selectedSpouseArea = binding.spinnerSpouseArea.selectedItem.toString()
        val checkedTermsOfUse = binding.signupCheckbox.isChecked

        //이메일
        if (email.isEmpty()) {
            binding.edtEmail.requestFocus()
            binding.txtEmail2.text = "이메일을 입력해 주세요."
            binding.txtEmail2.setTextColor(ContextCompat.getColor(applicationContext!!,R.color.error))

            binding.edtEmail.addTextChangedListener {
                if (it.toString().isNotEmpty()) {
                    binding.txtEmail2.setText(R.string.signcondent)
                    binding.txtEmail2.setTextColor(ContextCompat.getColor(applicationContext!!,R.color.black))
                }
            }
            return
        }
        //이메일 @ 뒷주소
        if (selectedEmailCompany.contains("직접") && (edtEmailCompany.isEmpty()) ){
            binding.edtDirectInput.requestFocus()
            binding.txtErrorEmailMsg.isVisible = true
            binding.edtDirectInput.addTextChangedListener {
                val content = it.toString()
                if (content.isNotEmpty()){
                    binding.txtErrorEmailMsg.isVisible = false
                }
            }
            return
        }
        //비밀번호
        if (!Pattern.matches("^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{6,12}$",pw)) { //영어 숫자(길이:6~12)가 아니면 실행
            binding.edtPw.requestFocus() //포커스 요청
            binding.txtPwErrorMSG.visibility = View.VISIBLE
            binding.edtPw.addTextChangedListener {
                val pwContent = it.toString()
                if (Pattern.matches("^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{6,12}$",pwContent)){
                    binding.txtPwErrorMSG.visibility = View.GONE
                }
                else{
                    binding.txtPwErrorMSG.visibility = View.VISIBLE

                }

            }
            return
        }
        //비밀번호 확인
        if (rePw != pw) {
            binding.edtRePw.requestFocus()
            binding.txtReErrorMSG.text = "비밀번호를 재입력해주세요."
            binding.txtReErrorMSG.setTextColor(ContextCompat.getColor(applicationContext!!,R.color.error))

            binding.edtRePw.addTextChangedListener {
                val rePw2 = it.toString()
                if(pw == rePw2){
                    binding.txtReErrorMSG.text = ""
                }
                else{
                    binding.txtReErrorMSG.text = "입력하신 비밀번호가 같지 않습니다."
                    binding.txtReErrorMSG.setTextColor(ContextCompat.getColor(applicationContext!!,R.color.error))
                }
            }
            return
        }

        //대화명
        if (!Pattern.matches("^(?=.*[ㄱ-ㅎ가-힣])(?=.*[0-9])[ㄱ-ㅎ가-힣[0-9]]{2,8}$",nickname)){ //한글 숫자(길이: 2~8)이 아니면 실행
            binding.edtNickname.requestFocus()
            binding.txtNicnameErrorMSG.visibility = View.VISIBLE

            binding.edtNickname.addTextChangedListener {
                val edtNickname2 = it.toString()
                if (Pattern.matches("^(?=.*[ㄱ-ㅎ가-힣])(?=.*[0-9])[ㄱ-ㅎ가-힣[0-9]]{2,8}$",edtNickname2)){
                    binding.txtNicnameErrorMSG.visibility = View.GONE
                }
                else {
                    binding.txtNicnameErrorMSG.visibility = View.VISIBLE

                }
            }
            return
        }
        //성별
        if (!genderClicked) {
            binding.txtGender.text = "성별을 선택하세요."
            binding.txtGender.setTextColor(ContextCompat.getColor(applicationContext!!,R.color.error)) //텍스트뷰컬러 바꾸기
            binding.imgMan.requestFocus()
            return
        }
        //태어난 연도
        if(selectedBirthResult()){

            return
            }
        //나의 지역 선택
        if (selectedAreaResult()){
            return
        }

        //배우자 희망 지역
        if (selectedSpouseArea.contains("배우자")){
            binding.txtSpinnerErrorSpouseArea.visibility = View.VISIBLE
            binding.txtSpouseArea.text = "배우자 희망지역을 선택해주세요."
            binding.txtSpouseArea.setTextColor(ContextCompat.getColor(applicationContext!!,R.color.error))
            return
        }
        if (!checkedTermsOfUse){
            Toast.makeText(mContext, "이용약관에 동의해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

    }

    //연도 선택함수
    fun selectedBirthResult(): Boolean{
        return if (selectedBirthYear.contains("태어난")) {
            binding.txtSpinnerError.visibility = View.VISIBLE
            binding.txtBirthYear.text = "연도를 선택해주세요"
            binding.txtBirthYear.setTextColor(ContextCompat.getColor(applicationContext!!,R.color.error))

            true
        } else{
            binding.txtSpinnerError.visibility= View.GONE
            binding.txtBirthYear.text = "태어난 연도"
            binding.txtBirthYear.setTextColor(ContextCompat.getColor(applicationContext!!,R.color.black))
            false
        }
    }
    //나의 지역함수
    fun selectedAreaResult(): Boolean{
        return if (selectedMyArea.contains("현재")) {
            binding.txtSpinnerErrorArea.visibility = View.VISIBLE
            binding.txtMyArea.text = "현재 거주지역을 선택해주세요"
            binding.txtMyArea.setTextColor(ContextCompat.getColor(applicationContext!!,R.color.error))

            true
        } else{
            binding.txtSpinnerErrorArea.visibility= View.GONE
            binding.txtMyArea.text = "나의 지역"
            binding.txtMyArea.setTextColor(ContextCompat.getColor(applicationContext!!,R.color.black))
            false
        }
    }




    //자주 사용하는 유효성 검사 정규식-----------
//    private fun isRegularPW(password: String): Boolean {
//        val pwPattern1 =
//            "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{8,20}$" // 영문, 숫자 8~20글자
//        val pwPattern2 =
//            "^(?=.*[0-9])(?=.*[$@$!%*#?&.])[[0-9]$@$!%*#?&.]{8,20}$" // 숫자, 특수문자
//        val pwPattern3 =
//            "^(?=.*[A-Za-z])(?=.*[$@$!%*#?&.])[A-Za-z$@$!%*#?&.]{8,20}$" // 영문, 특수문자
//        val pwPattern4 =
//            "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&.])[A-Za-z[0-9]$@$!%*#?&.]{8,20}$" // 영문, 숫자, 특수문자
//
//        val pattern = Pattern.compile(pwPattern1)
//        val matcher = pattern.matcher(pwPattern1)
//        Log.d("Match", matcher.find().toString())
//
//        return (Pattern.matches(pwPattern1, password) ||
//                Pattern.matches(pwPattern2, password) ||
//                Pattern.matches(pwPattern3, password) ||
//                Pattern.matches(pwPattern4, password))
//    }
//
}
    



