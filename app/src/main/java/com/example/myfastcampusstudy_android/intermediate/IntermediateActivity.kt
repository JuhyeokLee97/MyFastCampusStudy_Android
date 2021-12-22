package com.example.myfastcampusstudy_android.intermediate

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import com.example.myfastcampusstudy_android.R
import com.example.myfastcampusstudy_android.databinding.ActivityIntermediateBinding
import com.example.myfastcampusstudy_android.intermediate.abnb.AbnbMainActivity
import com.example.myfastcampusstudy_android.intermediate.book_review.BookReviewMainActivity
import com.example.myfastcampusstudy_android.intermediate.used_transaction.UsedTransactionMainActivity

class IntermediateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntermediateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intermediate)
        setListener()
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        binding = ActivityIntermediateBinding.bind(parent!!)
        return super.onCreateView(parent, name, context, attrs)
    }

    private fun setListener() {
        setListenerToButtonForBookReview()
        setListenerToButtonForAirBNB()
        setListenerToButtonForUsedTransaction()
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