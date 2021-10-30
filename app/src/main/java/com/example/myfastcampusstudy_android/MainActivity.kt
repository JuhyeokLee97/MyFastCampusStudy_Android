package com.example.myfastcampusstudy_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.myfastcampusstudy_android.basic.CalculationBmiMainActivity

class MainActivity : AppCompatActivity() {
    lateinit var  btnBasic: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnBasic = findViewById(R.id.ibBasicProjects)
        btnBasic.setOnClickListener {
            var intent = Intent(this, CalculationBmiMainActivity::class.java)
            startActivity(intent)
        }
    }
}