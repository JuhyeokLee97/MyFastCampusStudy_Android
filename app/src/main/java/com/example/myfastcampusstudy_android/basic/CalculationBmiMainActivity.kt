package com.example.myfastcampusstudy_android.basic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.myfastcampusstudy_android.R

class CalculationBmiMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculation_bmi_main)

        val etHeight: EditText = findViewById(R.id.etHeight)
        val etWeight: EditText = findViewById(R.id.etWeight)
        val btnCalculation: Button = findViewById(R.id.btnCalculation)
    }
}