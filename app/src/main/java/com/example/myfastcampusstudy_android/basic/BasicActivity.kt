package com.example.myfastcampusstudy_android.basic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.AppCompatButton
import com.example.myfastcampusstudy_android.R
import com.example.myfastcampusstudy_android.basic.bmi_calculator.CalculationBmiMainActivity
import com.example.myfastcampusstudy_android.basic.calculator.CalculatorMainActivity
import com.example.myfastcampusstudy_android.basic.lottery_number_recommendation.LotteryNumberRecommendationMainActivity
import com.example.myfastcampusstudy_android.basic.picture_frame.PictureFrameMainActivity
import com.example.myfastcampusstudy_android.basic.pomodoro_timer.PomodoroTimerMainActivity
import com.example.myfastcampusstudy_android.basic.recorder.RecorderMainActivity
import com.example.myfastcampusstudy_android.basic.secret_diary.SecretDiaryMainActivity
import com.example.myfastcampusstudy_android.basic.simple_web_browser.SimpleWebBrowserActivity

class BasicActivity : AppCompatActivity() {
    lateinit var btnMoveToCalculationBmiMainActivity: AppCompatButton
    lateinit var btnMoveToLotteryNumberRecommendationMainActivity: AppCompatButton
    lateinit var btnMoveToSecretDiaryMainActivity: AppCompatButton
    lateinit var btnMoveToPictureFrameMainActivity: AppCompatButton
    lateinit var btnMoveToPomodoroTimerMainActivity: AppCompatButton
    lateinit var btnMoveToRecorderMainActivity: AppCompatButton
    lateinit var btnMoveToSimpleWebBrowserActivity: AppCompatButton

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

        btnMoveToPomodoroTimerMainActivity = findViewById(R.id.btnMoveToPomodoroTimer)
        btnMoveToPomodoroTimerMainActivity.setOnClickListener {
            startActivity(Intent(this, PomodoroTimerMainActivity::class.java))
        }

        btnMoveToRecorderMainActivity = findViewById(R.id.btnMoveToRecorder)
        btnMoveToRecorderMainActivity.setOnClickListener {
            startActivity(Intent(this, RecorderMainActivity::class.java))
        }

        btnMoveToSimpleWebBrowserActivity = findViewById(R.id.btnMoveToSimpleWebBrowser)
        btnMoveToSimpleWebBrowserActivity.setOnClickListener {
            startActivity(Intent(this, SimpleWebBrowserActivity::class.java))
        }
    }
}