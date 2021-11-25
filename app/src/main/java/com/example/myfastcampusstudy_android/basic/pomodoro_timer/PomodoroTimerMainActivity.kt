package com.example.myfastcampusstudy_android.basic.pomodoro_timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
                tvRemainMinutes.text = "%02d".format(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
    }

}