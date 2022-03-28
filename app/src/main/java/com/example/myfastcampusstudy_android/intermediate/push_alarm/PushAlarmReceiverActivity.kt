package com.example.myfastcampusstudy_android.intermediate.push_alarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myfastcampusstudy_android.R
import com.example.myfastcampusstudy_android.databinding.ActivityPushAlarmReceiverBinding

class PushAlarmReceiverActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPushAlarmReceiverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPushAlarmReceiverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
    }
}