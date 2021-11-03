package com.example.myfastcampusstudy_android.basic.bmi_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.myfastcampusstudy_android.R
import kotlin.math.pow

class ResultActivity : AppCompatActivity() {
    val TAG = "ResultActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val height = intent.getIntExtra("height", 0)
        val weight = intent.getIntExtra("weight", 0)

        Log.d(TAG, "height: $height \t weight: $weight")

        val bmi = weight / (height / 100.0).pow(2.0)
        val resultStatus = when{
            bmi >= 35.0 -> "고도 비만"
            bmi >= 30.0 -> "중정도 비만"
            bmi >= 25.0 -> "경도 비만"
            bmi >= 23.0 -> "과체중"
            bmi >= 18.5 -> "정상체중"
            else -> "저체중"
        }

        val tvBmiValue: TextView = findViewById(R.id.tvBmiValue)
        val tvResultStatus: TextView = findViewById(R.id.tvResultStatus)

        tvBmiValue.text = bmi.toString()
        tvResultStatus.text = resultStatus
    }
}