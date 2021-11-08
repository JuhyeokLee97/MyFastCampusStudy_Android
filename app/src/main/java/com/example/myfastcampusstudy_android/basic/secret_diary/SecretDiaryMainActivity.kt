package com.example.myfastcampusstudy_android.basic.secret_diary

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit
import com.example.myfastcampusstudy_android.R

class SecretDiaryMainActivity : AppCompatActivity() {
    private var isChangePasswordMode = false

    lateinit var numberPicker1: NumberPicker
    lateinit var numberPicker2: NumberPicker
    lateinit var numberPicker3: NumberPicker

    lateinit var btnChangePassword: AppCompatButton
    lateinit var btnOpenDiary: AppCompatButton

    lateinit var passwordFromUser: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secret_diary_main)

        initView()
        setListener()
    }

    private fun initView() {
        numberPicker1 = findViewById<NumberPicker>(R.id.numberPicker1).apply {
            minValue = 0
            maxValue = 9
        }

        numberPicker2 = findViewById<NumberPicker>(R.id.numberPicker2).apply {
            minValue = 0
            maxValue = 9
        }

        numberPicker3 = findViewById<NumberPicker>(R.id.numberPicker3).apply {
            minValue = 0
            maxValue = 9
        }

        btnOpenDiary = findViewById(R.id.btnOpenDiary)
        btnChangePassword = findViewById(R.id.btnChangePassword)
    }

    private fun setListener() {
        btnOpenDiary.setOnClickListener {
            if (isChangePasswordMode) {
                Toast.makeText(this, "비밀번호를 변경 중입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            passwordFromUser = getPasswordFromNumberPicker()

            if (passwordPreferences.getString("password", "000").equals(passwordFromUser)) {
                Toast.makeText(this, "다이어리로 접속 중...", Toast.LENGTH_SHORT).show()
            } else {
                AlertDialog.Builder(this)
                    .setTitle("실패")
                    .setMessage("비밀번호가 틀렸습니다.")
                    .setPositiveButton("확인") { _, _ -> }
                    .create()
                    .show()
            }

        }
        btnChangePassword.setOnClickListener {
            if (isChangePasswordMode) {     // 변경된 비밀 번호 저장
                passwordFromUser = getPasswordFromNumberPicker()
                val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
                passwordPreferences.edit(commit = true) {
                    putString("password", passwordFromUser)
                }
                btnChangePassword.setBackgroundColor(Color.BLACK)
                isChangePasswordMode = false
            } else {                        // 비밀 번호 변경 모드 전환
                isChangePasswordMode = true
                val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
                passwordFromUser = getPasswordFromNumberPicker()

                if (passwordPreferences.getString("password", "000").equals(passwordFromUser)) {
                    isChangePasswordMode = true
                    Toast.makeText(this, "변경할 패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show()
                    btnChangePassword.setBackgroundColor(Color.RED)
                } else {
                    AlertDialog.Builder(this)
                        .setTitle("실패")
                        .setMessage("비밀번호가 틀렸습니다.")
                        .setPositiveButton("확인") { _, _ -> }
                        .create()
                        .show()
                }
            }
        }
    }

    private fun getPasswordFromNumberPicker(): String {
        return "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"
    }
}