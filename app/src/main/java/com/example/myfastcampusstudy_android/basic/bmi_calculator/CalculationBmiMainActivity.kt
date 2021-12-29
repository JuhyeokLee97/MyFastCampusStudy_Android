package com.example.myfastcampusstudy_android.basic.bmi_calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.myfastcampusstudy_android.R
import com.example.myfastcampusstudy_android.databinding.ActivityCalculationBmiMainBinding

class CalculationBmiMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalculationBmiMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculationBmiMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        initCalculationButton()
    }

    private fun initCalculationButton() {
        binding.apply {
            btnCalculation.setOnClickListener {
                if (etHeight.text.isEmpty() or etWeight.text.isEmpty()) {
                    Toast.makeText(
                        this@CalculationBmiMainActivity,
                        "내용 모두를 채워주세요.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                val height = etHeight.text.toString().toInt()
                val weight = etWeight.text.toString().toInt()

                val intent = Intent(this@CalculationBmiMainActivity, ResultActivity::class.java)
                intent.putExtra("height", height)
                intent.putExtra("weight", weight)
                startActivity(intent)
            }

        }
    }
}