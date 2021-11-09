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
    private lateinit var numberPicker1: NumberPicker
    private lateinit var numberPicker2: NumberPicker
    private lateinit var numberPicker3: NumberPicker

    private lateinit var btnOpenDiary: AppCompatButton
    private lateinit var btnChangePassword: AppCompatButton

    var changePasswordMode = false

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

    private fun setListener(){
        btnOpenDiary.setOnClickListener {
            if (changePasswordMode){
                Toast.makeText(this, "비밀번호 변경 중... 지금은 로그인 할 수 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            val passwordFromUser = getPasswordFromUser()

            if (passwordPreferences.getString("password", "000").equals(passwordFromUser)){
                // Success
                Toast.makeText(this, "로그인 성공 하였습니다.", Toast.LENGTH_SHORT).show()
            }else{
                AlertDialog.Builder(this)
                    .setTitle("실패")
                    .setMessage("비밀번호가 잘못되었습니다.")
                    .setPositiveButton("확인") {_, _, -> }
                    .create()
                    .show()
            }
        }

        btnChangePassword.setOnClickListener {
            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)

            if (changePasswordMode){    // 비밀번호 변경 중 -> 완료
                Toast.makeText(this, "비밀번호 ${getPasswordFromUser()}로 변경 됨", Toast.LENGTH_SHORT).show()
                it.setBackgroundColor(Color.BLACK)
                changePasswordMode = false
                passwordPreferences.edit(true){
                    putString("password", getPasswordFromUser())
                }
            }else{  // -> 비밀번호 변경 가능 모드
                if(passwordPreferences.getString("password", "000").equals(getPasswordFromUser())){ // 입력된 비밀번호가 같은 경우 => 비밀번호 수정 가능
                    Toast.makeText(this, "비밀번호를 변경 모드 ON", Toast.LENGTH_SHORT).show()
                    it.setBackgroundColor(Color.RED)
                    changePasswordMode = true
                }else{  // 입력된 비밀번호가 달라 수정 X
                    Toast.makeText(this, "입력된 비밀번호가 달라 변경할 수 없습니다.", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun getPasswordFromUser(): String{
        return "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"
    }
}