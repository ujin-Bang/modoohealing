package com.modoohealing.modo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import com.modoohealing.modo.api.ServerAPI
import com.modoohealing.modo.api.ServerAPIService

abstract class BaseActivity : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var btnBack: ImageButton
    lateinit var txtMainName: TextView
    private lateinit var txtSignup: TextView
    lateinit var layoutSignup: LinearLayout

    lateinit var apiService: ServerAPIService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = this
        val retrofit = ServerAPI.getRetrofit()
        apiService = retrofit.create(ServerAPIService::class.java)

        //액션바가 있는 화면에서만 커스텀액션바 실행.
        supportActionBar?.let {
            setCustomActionBar()
        }

    }

    abstract fun setupEvents()

    abstract fun setValues()

    //액션바 커스텀
   fun setCustomActionBar(){
       val defActionBar = supportActionBar!! //기본액션바 가져오기

       defActionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM //액션바를 커스텀 모드로 변경

       defActionBar.setCustomView(R.layout.base_custom_action_bar)//실제 커스텀뷰를 어떤 모양으로 할건지

       val toolBar = defActionBar.customView.parent as androidx.appcompat.widget.Toolbar
       toolBar.setContentInsetsAbsolute(0,0)

        btnBack = defActionBar.customView.findViewById(R.id.btnBack)
        txtMainName = defActionBar.customView.findViewById(R.id.txtMainName)
        txtSignup = defActionBar.customView.findViewById(R.id.txtSignup)
        layoutSignup = defActionBar.customView.findViewById(R.id.linearLayoutSignup)

   }
}