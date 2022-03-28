package com.example.myfastcampusstudy_android.intermediate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myfastcampusstudy_android.databinding.ActivityIntermediateBinding
import com.example.myfastcampusstudy_android.intermediate.abnb.AbnbMainActivity
import com.example.myfastcampusstudy_android.intermediate.book_review.BookReviewMainActivity
import com.example.myfastcampusstudy_android.intermediate.push_alarm.PushAlarmReceiverActivity
import com.example.myfastcampusstudy_android.intermediate.tinder.TinderMainActivity
import com.example.myfastcampusstudy_android.intermediate.used_transaction.UsedTransactionMainActivity

class IntermediateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntermediateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntermediateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners()
    }


    private fun setListeners() {
        setListenerToButtonForBookReview()
        setListenerToButtonForAirBNB()
        setListenerToButtonForUsedTransaction()
        setListenerToButtonForTinder()
        setListenerToButtonForPushAlarmReceiver()
    }

    private fun setListenerToButtonForPushAlarmReceiver() {
        binding.btnMoveToPushAlarmReceiver.setOnClickListener {
            val intent = Intent(this, PushAlarmReceiverActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setListenerToButtonForTinder() {
        binding.btnMoveToTinder.setOnClickListener {
            val intent = Intent(this, TinderMainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setListenerToButtonForBookReview() {
        binding.btnMoveToBookReview.setOnClickListener {
            val intent = Intent(this, BookReviewMainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setListenerToButtonForAirBNB() {
        binding.btnMoveToAirBNB.setOnClickListener {
            val intent = Intent(this, AbnbMainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setListenerToButtonForUsedTransaction() {
        binding.btnMoveToUsedTransaction.setOnClickListener {
            val intent = Intent(this, UsedTransactionMainActivity::class.java)
            startActivity(intent)
        }
    }
}