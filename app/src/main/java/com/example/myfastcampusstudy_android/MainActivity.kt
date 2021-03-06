package com.example.myfastcampusstudy_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatButton
import com.example.myfastcampusstudy_android.advanced.ToDoActivity
import com.example.myfastcampusstudy_android.basic.BasicActivity
import com.example.myfastcampusstudy_android.databinding.ActivityMainBinding
import com.example.myfastcampusstudy_android.intermediate.IntermediateActivity
import com.example.myfastcampusstudy_android.intermediate.abnb.AbnbMainActivity
import com.example.myfastcampusstudy_android.upper_intermediate.UpperIntermediateActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        setListeners()
    }

    private fun initViews() {
        initBasicButton()
        initIntermediateButton()
        initUpperIntermediateButton()

    }

    private fun setListeners() {
        setListenerToAdvancedButton()
    }

    private fun setListenerToAdvancedButton() {
        binding.btnAdvancedProject.setOnClickListener {
            val intent = Intent(this, ToDoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initBasicButton() {
        binding.btnBasicProject.setOnClickListener {
            val intent = Intent(this, BasicActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initIntermediateButton() {
        binding.btnIntermediateProject.setOnClickListener {
            val intent = Intent(this, IntermediateActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initUpperIntermediateButton() {
        binding.btnUpperIntermediateProject.setOnClickListener {
            val intent = Intent(this, UpperIntermediateActivity::class.java)
            startActivity(intent)
        }
    }
}