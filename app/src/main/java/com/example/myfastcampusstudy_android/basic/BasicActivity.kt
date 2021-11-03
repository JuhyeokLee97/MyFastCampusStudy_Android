package com.example.myfastcampusstudy_android.basic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.myfastcampusstudy_android.R
import com.example.myfastcampusstudy_android.basic.bmi_calculator.CalculationBmiMainActivity
import com.example.myfastcampusstudy_android.basic.lottery_number_recommendation.LotteryNumberRecommendationMainActivity

class BasicActivity : AppCompatActivity() {
    lateinit var btnMoveToCalculationBmiMainActivity: Button
    lateinit var btnMoveToLotteryNumberRecommendationMainActivity: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic)

        initView()
    }

    private fun initView() {
        btnMoveToCalculationBmiMainActivity = findViewById(R.id.btnMoveToBmiCalculator)
        btnMoveToCalculationBmiMainActivity.setOnClickListener {
            startActivity(Intent(this, CalculationBmiMainActivity::class.java))
        }

        btnMoveToLotteryNumberRecommendationMainActivity =
            findViewById(R.id.btnMoveToLotteryNumberRecommendation)
        btnMoveToLotteryNumberRecommendationMainActivity.setOnClickListener {
            startActivity(Intent(this, LotteryNumberRecommendationMainActivity::class.java))
        }
    }
}