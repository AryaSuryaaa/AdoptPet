package com.aryasurya.adoptpet.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aryasurya.adoptpet.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val fragmentManager = supportFragmentManager
//        val loginFragment = LoginFragment()
//        val fragment = fragmentManager.findFragmentByTag(LoginFragment::class.java.simpleName)
//
//        if (fragment !is LoginFragment) {
//            fragmentManager
//                .beginTransaction()
//                .add(R.id.frame_container, loginFragment, LoginFragment::class.java.simpleName)
//                .commit()
//        }
    }
}