package com.example.myfastcampusstudy_android.basic.bmi_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myfastcampusstudy_android.R
import com.example.myfastcampusstudy_android.databinding.ActivityResultBinding
import kotlin.math.pow

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private var bmi: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        initBmiValueTextView()
        initResultStatusTextView(bmi)
    }

    private fun initBmiValueTextView() {
        val height = intent.getIntExtra("height", 0)
        val weight = intent.getIntExtra("weight", 0)

        bmi = weight / (height / 100.0).pow(2.0)

        binding.tvBmiValue.text = bmi.toString()
    }

    private fun initResultStatusTextView(bmi: Double) {
        val resultStatus = when {
            bmi >= 35.0 -> getString(R.string.text_bmi_level_5)
            bmi >= 30.0 -> getString(R.string.text_bmi_level_4)
            bmi >= 25.0 -> getString(R.string.text_bmi_level_3)
            bmi >= 23.0 -> getString(R.string.text_bmi_level_2)
            bmi >= 18.5 -> getString(R.string.text_bmi_level_1)
            else -> getString(R.string.text_bmi_level_0)
        }

        binding.tvResultStatus.text = resultStatus
    }

}