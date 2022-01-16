package com.example.myfastcampusstudy_android.upper_intermediate.youtube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myfastcampusstudy_android.R
import com.example.myfastcampusstudy_android.databinding.ActivityYoutubeMainBinding

class YoutubeMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityYoutubeMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYoutubeMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPlayerFragment()

    }

    private fun initPlayerFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PlayerFragment())
            .commit()
    }
}