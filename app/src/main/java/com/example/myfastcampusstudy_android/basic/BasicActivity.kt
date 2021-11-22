package com.example.myfastcampusstudy_android.basic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.myfastcampusstudy_android.R
import com.example.myfastcampusstudy_android.basic.bmi_calculator.CalculationBmiMainActivity
import com.example.myfastcampusstudy_android.basic.calculator.CalculatorMainActivity
import com.example.myfastcampusstudy_android.basic.lottery_number_recommendation.LotteryNumberRecommendationMainActivity
import com.example.myfastcampusstudy_android.basic.picture_frame.PictureFrameMainActivity
import com.example.myfastcampusstudy_android.basic.secret_diary.SecretDiaryMainActivity

class BasicActivity : AppCompatActivity() {
    lateinit var btnMoveToCalculationBmiMainActivity: Button
    lateinit var btnMoveToLotteryNumberRecommendationMainActivity: Button
    lateinit var btnMoveToSecretDiaryMainActivity: Button
    lateinit var btnMoveToPictureFrameMainActivity: Button

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

        btnMoveToSecretDiaryMainActivity = findViewById(R.id.btnMoveToSecretDiary)
        btnMoveToSecretDiaryMainActivity.setOnClickListener {
            startActivity(Intent(this, SecretDiaryMainActivity::class.java))
        }

        findViewById<Button>(R.id.btnMoveToCalculator).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    CalculatorMainActivity::class.java
                )
            )
        }

        btnMoveToPictureFrameMainActivity = findViewById(R.id.btnMoveToPictureFrame)
        btnMoveToPictureFrameMainActivity.setOnClickListener {
            startActivity(Intent(this, PictureFrameMainActivity::class.java))
        }
    }
}