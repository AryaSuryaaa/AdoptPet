package com.aryasurya.adoptpet.ui.detailpost

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.aryasurya.adoptpet.R
import com.aryasurya.adoptpet.data.Result
import com.aryasurya.adoptpet.databinding.ActivityDetailPostBinding
import com.aryasurya.adoptpet.helper.isInternetAvailable
import com.aryasurya.adoptpet.ui.ViewModelFactory
import com.aryasurya.adoptpet.ui.list.ListStoryViewModel
import com.bumptech.glide.Glide

class DetailPostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPostBinding
    private val viewModel by viewModels<ListStoryViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receivedId = intent.getStringExtra("idUser").toString()
        val receivedName = intent.getStringExtra("name").toString()
        val receivedDesc = intent.getStringExtra("desc").toString()
        val receivedPhoto = intent.getStringExtra("photo").toString()
        viewModel.detailStory(receivedId)


        viewModel.detailResult.observe(this) { result ->
            when(result) {
                is Result.Loading -> {}
                is Result.Success -> {
                    setDetailStory(
                        result.data.name ,
                        result.data.photoUrl ,
                        result.data.description
                    )
                }
                is Result.Error -> {
                    if (!isInternetAvailable(this)) {
                        setDetailStory(receivedName, receivedPhoto, receivedDesc)
                    }
                    Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

        binding.fabDetail.setOnClickListener {
            finish()
        }
    }

    private fun setDetailStory(name: String, img: String, desc: String) {
        binding.tvNameDetail.text = name
        binding.tvDescDetail.text = desc
        Glide.with(applicationContext)
            .load(img)
            .into(binding.ivDetail)

    }
}