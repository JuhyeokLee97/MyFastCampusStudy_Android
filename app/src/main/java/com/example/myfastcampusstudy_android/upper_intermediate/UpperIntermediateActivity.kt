package com.example.myfastcampusstudy_android.upper_intermediate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myfastcampusstudy_android.R
import com.example.myfastcampusstudy_android.databinding.ActivityUpperIntermediateBinding
import com.example.myfastcampusstudy_android.upper_intermediate.youtube.YoutubeMainActivity

class UpperIntermediateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpperIntermediateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpperIntermediateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        initYoutubeButton()
    }

    private fun initYoutubeButton() {
        binding.btnMoveToYoutube.setOnClickListener {
            val intent = Intent(this, YoutubeMainActivity::class.java)
            startActivity(intent)
        }
    }
}