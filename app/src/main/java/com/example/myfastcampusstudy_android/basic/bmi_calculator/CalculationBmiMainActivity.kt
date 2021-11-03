package com.example.myfastcampusstudy_android.basic.bmi_calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.myfastcampusstudy_android.R

class CalculationBmiMainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculation_bmi_main)

        val etHeight: EditText = findViewById(R.id.etHeight)
        val etWeight: EditText = findViewById(R.id.etWeight)
        val btnCalculation: Button = findViewById(R.id.btnCalculation)

        btnCalculation.setOnClickListener {
            Log.d(TAG, "버튼 클릭")

            if (etHeight.text.isEmpty() or etWeight.text.isEmpty()) {
                Toast.makeText(this, "내용 모두를 채워주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val height = etHeight.text.toString().toInt()
            val weight = etWeight.text.toString().toInt()
            Log.d(TAG, "입력된 Height: $height \t Weight: $weight")

            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("height", height)
            intent.putExtra("weight", weight)
            startActivity(intent)
        }
    }
}