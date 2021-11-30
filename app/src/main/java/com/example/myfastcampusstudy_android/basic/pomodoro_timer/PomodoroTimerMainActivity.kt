package com.example.myfastcampusstudy_android.basic.pomodoro_timer

import android.media.SoundPool
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

    private val soundPool = SoundPool.Builder().build()
    private var tickingSoundId: Int? = null
    private var bellSoundId: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pomodoro_timer_main)

        initView()
        initSound()
    }

    override fun onResume() {
        super.onResume()
        soundPool.autoResume()
    }

    override fun onPause() {
        super.onPause()
        soundPool.autoPause()
    }

    override fun onDestroy() {
        super.onDestroy()

        soundPool.release()
    }

    private fun initView() {
        bindSeekBar()
    }

    private fun bindSeekBar() {
        seekBar = findViewById(R.id.seekBar)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser)
                    updateRemainTimes(progress * 60 * 1000L)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                stopCountDown()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar ?: return

                if(seekBar.progress == 0){
                    stopCountDown()
                }else {
                    startCountDown()
                }
            }
        })
    }

    private fun stopCountDown() {
        currentCountDownTimer?.cancel()
        currentCountDownTimer = null

        soundPool.autoPause()
    }


    private fun startCountDown() {
        currentCountDownTimer = createCountDownTimer(seekBar.progress * 60 * 1000L)
        currentCountDownTimer!!.start()

        tickingSoundId?.let {
            soundPool.play(it, 1F, 1F, 0, -1, 1F)
        }
    }

    private fun createCountDownTimer(initialMillis: Long) =
        object : CountDownTimer(initialMillis, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                updateRemainTimes(millisUntilFinished)
                updateSeekBar(millisUntilFinished)
            }

            override fun onFinish() {   // 0으로 초기화
                completeCountDown()
            }
        }

    private fun completeCountDown() {
        updateRemainTimes(0)
        updateSeekBar(0)

        soundPool.autoPause()
        bellSoundId?.let {
            soundPool.play(it, 1F, 1F, 0, 0, 1F)
        }
    }

    private fun updateRemainTimes(remainMillis: Long) {
        val remainSeconds = remainMillis / 1000
        val remainMinutes = remainSeconds / 60

        tvRemainMinutes.text = "%02d'".format(remainMinutes)
        tvRemainSeconds.text = "%02d".format(remainSeconds % 60)
    }

    private fun updateSeekBar(remainMillis: Long) {
        val remainSeconds = remainMillis / 1000
        val remainMinutes = remainSeconds / 60

        seekBar.progress = remainMinutes.toInt()
    }

    private fun initSound() {
        tickingSoundId = soundPool.load(this, R.raw.timer_ticking, 1)
        bellSoundId = soundPool.load(this, R.raw.timer_bell, 1)
    }
}