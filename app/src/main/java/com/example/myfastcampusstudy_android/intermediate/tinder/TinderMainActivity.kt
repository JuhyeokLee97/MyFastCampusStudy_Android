package com.example.myfastcampusstudy_android.intermediate.tinder

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.example.myfastcampusstudy_android.R
import com.example.myfastcampusstudy_android.databinding.ActivityTinderMainBinding

class TinderMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTinderMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTinderMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}