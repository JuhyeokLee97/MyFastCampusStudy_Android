package com.example.myfastcampusstudy_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatButton
import com.example.myfastcampusstudy_android.basic.BasicActivity
import com.example.myfastcampusstudy_android.intermediate.abnb.AbnbMainActivity

class MainActivity : AppCompatActivity() {
    lateinit var btnBasic: ImageButton
    lateinit var btnIntermediate: AppCompatButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnBasic = findViewById(R.id.ibBasicProjects)
        btnBasic.setOnClickListener {
            var intent = Intent(this, BasicActivity::class.java)
            startActivity(intent)
        }

        btnIntermediate = findViewById(R.id.btnIntermediateProject)
        btnIntermediate.setOnClickListener {
            val intent = Intent(this, AbnbMainActivity::class.java)
            startActivity(intent)
        }
    }
}