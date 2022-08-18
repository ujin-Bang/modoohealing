package com.modoohealing.modo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.modoohealing.modo.databinding.ActivitySplashBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity() {
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash)

        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        CoroutineScope(Dispatchers.IO).launch{

            delay(2500)
            startActivity(Intent(mContext, LoginActivity::class.java))
            finish()

        }
    }

    override fun setValues() {


    }
}