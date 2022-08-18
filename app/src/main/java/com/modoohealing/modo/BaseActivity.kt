package com.modoohealing.modo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBar

abstract class BaseActivity : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var btnBack: Button
    lateinit var txtMainName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = this

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




   }
}