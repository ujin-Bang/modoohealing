package com.modoohealing.modo.bottomnavigationitems

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.modoohealing.modo.BaseFragment
import com.modoohealing.modo.R
import com.modoohealing.modo.databinding.FragmentUsersBinding

class UsersFragment: BaseFragment() {

    lateinit var binding: FragmentUsersBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_users, container,false)
        return binding.root
    }

    override fun setupEvents() {
    }

    override fun setValues() {

    }
}