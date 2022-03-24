package com.example.myfastcampusstudy_android.basic.simple_web_browser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myfastcampusstudy_android.databinding.ActivitySimpleWebBrowserBinding

class SimpleWebBrowserActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySimpleWebBrowserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimpleWebBrowserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {

    }
}