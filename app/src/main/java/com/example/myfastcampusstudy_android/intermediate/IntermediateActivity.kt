package com.example.myfastcampusstudy_android.intermediate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import com.example.myfastcampusstudy_android.R
import com.example.myfastcampusstudy_android.intermediate.abnb.AbnbMainActivity
import com.example.myfastcampusstudy_android.intermediate.book_review.BookReviewMainActivity
import com.example.myfastcampusstudy_android.intermediate.used_transaction.UsedTransactionMainActivity

class IntermediateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intermediate)

        setListener()
    }

    private fun setListener() {
        setListenerToButtonForBookReview()
        setListenerToButtonForAirBNB()
        setListenerToButtonForUsedTransaction()
    }

    private fun setListenerToButtonForBookReview() {
        val btnMoveToBookReviewMainActivity =
            findViewById<AppCompatButton>(R.id.btnMoveToBookReview)
        btnMoveToBookReviewMainActivity.setOnClickListener {
            val intent = Intent(this, BookReviewMainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setListenerToButtonForAirBNB() {
        val btnMoveToAirBNBMainActivity = findViewById<AppCompatButton>(R.id.btnMoveToAirBNB)
        btnMoveToAirBNBMainActivity.setOnClickListener {
            val intent = Intent(this, AbnbMainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setListenerToButtonForUsedTransaction() {
        val btnMoveToUsedTransactionMainActivity =
            findViewById<AppCompatButton>(R.id.btnMoveToUsedTransaction)
        btnMoveToUsedTransactionMainActivity.setOnClickListener {
            val intent = Intent(this, UsedTransactionMainActivity::class.java)
            startActivity(intent)
        }
    }
}