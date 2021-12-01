package com.example.myfastcampusstudy_android.basic.recorder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myfastcampusstudy_android.R

class RecorderMainActivity : AppCompatActivity() {
    private lateinit var btnRecord: RecordButton
    private var state = State.BEFORE_RECORDING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recorder_main)

        initViews()
    }

    private fun initViews() {
        initRecordButton()
    }

    private fun initRecordButton() {
        btnRecord = findViewById(R.id.btnRecord)
        btnRecord.updateIconWithState(state)
    }
}