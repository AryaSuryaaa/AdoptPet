package com.aryasurya.adoptpet.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.aryasurya.adoptpet.R
import com.aryasurya.adoptpet.databinding.ActivityMainBinding
import com.aryasurya.adoptpet.ui.ViewModelFactory
import com.aryasurya.adoptpet.ui.addpost.AddPostActivity
import com.aryasurya.adoptpet.ui.login.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var nameUser = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayShowTitleEnabled(true)
        }

        val navView: BottomNavigationView = binding.navView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        navView.setupWithNavController(navController)

        // Observasi perubahan fragment
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val fragmentTitle = when (destination.id) {
                R.id.accountFragment -> nameUser
                R.id.listFragment -> getString(R.string.list_story)
                else -> getString(R.string.app_name)
            }

            supportActionBar?.title = fragmentTitle
        }

        mainViewModel.getSession().observe(this) { user ->
            nameUser = user.username
            if (user?.isLogin == false) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.addPostActivity ->  {
                startActivity(Intent(this, AddPostActivity::class.java))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}