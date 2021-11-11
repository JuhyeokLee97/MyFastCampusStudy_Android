package com.example.myfastcampusstudy_android.basic.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.myfastcampusstudy_android.R

class CalculatorMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator_main)
    }

    fun btnClicked(v: View) {
        Toast.makeText(this, "Button is clicked", Toast.LENGTH_SHORT).show()
    }

    fun clearBtnClicked(v: View) {
        Toast.makeText(this, "Clear Button is clicked", Toast.LENGTH_SHORT).show()
    }

    fun historyBtnClicked(v: View) {
        Toast.makeText(this, "History Button is clicked", Toast.LENGTH_SHORT).show()
    }

    fun resultBtnClicked(v: View) {
        Toast.makeText(this, "Result Button is clicked", Toast.LENGTH_SHORT).show()
    }
}