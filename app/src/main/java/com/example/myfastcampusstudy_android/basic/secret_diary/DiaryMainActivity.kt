package com.example.myfastcampusstudy_android.basic.secret_diary

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener
import com.example.myfastcampusstudy_android.R

class DiaryMainActivity : AppCompatActivity() {
    lateinit var diaryBodyPreferences: SharedPreferences
    lateinit var etDiaryBody: EditText
    val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_main)
        diaryBodyPreferences = getSharedPreferences("Diary", Context.MODE_PRIVATE)

        initView()
        setListener()
    }

    private fun initView() {
        etDiaryBody = findViewById(R.id.etDiaryBody)
        etDiaryBody.setText(diaryBodyPreferences.getString("Body", ""))
    }

    private fun setListener() {
        etDiaryBody.addTextChangedListener {
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, 500)
        }
    }

    private val runnable = Runnable {
        diaryBodyPreferences.edit {
            putString("Body", etDiaryBody.text.toString())
        }
    }
}