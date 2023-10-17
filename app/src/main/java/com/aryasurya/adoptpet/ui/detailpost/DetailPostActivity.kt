package com.aryasurya.adoptpet.ui.detailpost

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.aryasurya.adoptpet.data.Result
import com.aryasurya.adoptpet.databinding.ActivityDetailPostBinding
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

        // Di dalam sebuah Activity
        val receivedId = intent.getStringExtra("idUser")
        viewModel.detailStory(receivedId.toString())

        viewModel.detailResult.observe(this) { result ->
            when(result) {
                is Result.Loading -> {}
                is Result.Success -> {
                    setDetailStory(
                        result.data.name ,
                        result.data.photoUrl ,
                        result.data.description
                    )
                    Toast.makeText(this, "berhasil", Toast.LENGTH_SHORT).show()
                }
                is Result.Error -> {
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
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