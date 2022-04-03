package com.example.myfastcampusstudy_android.intermediate.push_alarm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myfastcampusstudy_android.R
import com.example.myfastcampusstudy_android.databinding.ActivityPushAlarmReceiverBinding
import com.google.firebase.messaging.FirebaseMessaging

class PushAlarmReceiverActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPushAlarmReceiverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPushAlarmReceiverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        setIntent(intent)
        updateResult(true)
    }

    private fun initViews() {
        initFirebase()
        updateResult()
    }

    private fun initFirebase(){
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    binding.tvFirebaseToken.text = task.result
                }
            }
    }

    private fun updateResult(isNewIntent: Boolean = false){
        binding.tvResult.text = intent.getStringExtra("notificationType") ?: "앱 런처" + if (isNewIntent){
            "(으)로 갱신했습니다."
        }else{
            "(으)로 실행했습니다."
        }
    }
}