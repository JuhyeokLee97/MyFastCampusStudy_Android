package com.example.myfastcampusstudy_android.basic.pomodoro_timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.SeekBar
import android.widget.TextView
import com.example.myfastcampusstudy_android.R

class PomodoroTimerMainActivity : AppCompatActivity() {

    private val tvRemainMinutes: TextView by lazy {
        findViewById(R.id.tvRemainMinutes)
    }
    private val tvRemainSeconds: TextView by lazy {
        findViewById(R.id.tvRemainSeconds)
    }
    private lateinit var seekBar: SeekBar
    private var currentCountDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pomodoro_timer_main)

        initView()
    }

    private fun initView() {
        bindSeekBar()
    }

    private fun bindSeekBar() {
        seekBar = findViewById(R.id.seekBar)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser)
                    updateRemainTimes(progress * 60 * 1000L)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                currentCountDownTimer?.cancel()
                currentCountDownTimer = null
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar ?: return
                currentCountDownTimer = createCountDownTimer(seekBar!!.progress * 60 * 1000L)
                currentCountDownTimer!!.start()
            }
        })
    }


    private fun createCountDownTimer(initialMillis: Long) =
        object : CountDownTimer(initialMillis, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                updateRemainTimes(millisUntilFinished)
                updateSeekBar(millisUntilFinished)
            }

            override fun onFinish() {   // 0으로 초기화
                updateRemainTimes(0)
                updateSeekBar(0)
            }
        }

    private fun updateRemainTimes(remainMillis: Long) {
        val remainSeconds = remainMillis / 1000
        val remainMinutes = remainSeconds / 60

        tvRemainMinutes.text = "%02d".format(remainMinutes)
        tvRemainSeconds.text = "%02d".format(remainSeconds % 60)
    }

    private fun updateSeekBar(remainMillis: Long) {
        val remainSeconds = remainMillis / 1000
        val remainMinutes = remainSeconds / 60

        seekBar.progress = remainMinutes.toInt()
    }
}