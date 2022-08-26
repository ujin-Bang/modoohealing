package com.modoohealing.modo

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.modoohealing.modo.bottomhome.HomeFragment
import com.modoohealing.modo.bottommore.SeeMoreFragment
import com.modoohealing.modo.bottommypage.MyPageFragment
import com.modoohealing.modo.bottomusers.UsersFragment
import com.modoohealing.modo.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding
    private val homeFragment by lazy { HomeFragment() }
    private val usersFragment by lazy { UsersFragment() }
    private val myPageFragment by lazy { MyPageFragment() }
    private val seeMoreFragment by lazy { SeeMoreFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        initBottomNavigationBar()
        changeFragment(homeFragment)

    }
    private fun initBottomNavigationBar(){
        binding.mainBottomNaivigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    changeFragment(homeFragment)
                }
                R.id.users ->{
                    changeFragment(usersFragment)
                }
                R.id.myPage ->{
                    changeFragment(myPageFragment)
                }
                R.id.seeMore ->{
                    changeFragment(seeMoreFragment)
                }
            }
            true
        }
    }

    private fun changeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .apply { replace(R.id.mainFragmentContainer, fragment)
                commit()
            }
    }

}