package com.example.myfastcampusstudy_android.basic.calculator

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.myfastcampusstudy_android.R

class CalculatorMainActivity : AppCompatActivity() {
    private lateinit var tvExpression: TextView
    private lateinit var tvResult: TextView
    private var isOperator = false
    private var hasOperator = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator_main)

        initView()
    }

    private fun initView() {
        tvExpression = findViewById(R.id.tvExpression)
        tvResult = findViewById(R.id.tvResult)

    }

    fun btnClicked(v: View) {
        Toast.makeText(this, "Button is clicked", Toast.LENGTH_SHORT).show()
        when (v.id) {
            R.id.btnNum0 -> numberBtnClicked("0")
            R.id.btnNum1 -> numberBtnClicked("1")
            R.id.btnNum2 -> numberBtnClicked("2")
            R.id.btnNum3 -> numberBtnClicked("3")
            R.id.btnNum4 -> numberBtnClicked("4")
            R.id.btnNum5 -> numberBtnClicked("5")
            R.id.btnNum6 -> numberBtnClicked("6")
            R.id.btnNum7 -> numberBtnClicked("7")
            R.id.btnNum8 -> numberBtnClicked("8")
            R.id.btnNum9 -> numberBtnClicked("9")
            R.id.btnMulti -> operatorBtnClicked("*")
            R.id.btnAdd -> operatorBtnClicked("+")
            R.id.btnSub -> operatorBtnClicked("-")
            R.id.btnDivide -> operatorBtnClicked("/")
            R.id.btnModulo -> operatorBtnClicked("%")


        }
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

    private fun numberBtnClicked(number: String) {
        if (isOperator) {
            tvExpression.append(" ")
        }
        isOperator = false

        val expressionText = tvExpression.text.split(" ")
        if (expressionText.isNotEmpty() && expressionText.last().length >= 15) {
            Toast.makeText(this, "15자리 까지만 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
            return
        } else if (expressionText.last().isEmpty() && number == "0") {
            Toast.makeText(this, "0은 제일 앞에 올 수 없습니다.", Toast.LENGTH_SHORT).show()
        }

        tvExpression.append(number)

        // TODO tvResult 실시간으로 계산 결과를 넣어야 하는 기능

    }

    private fun operatorBtnClicked(operator: String) {
        if (tvExpression.text.isEmpty())
            return

        when {
            isOperator -> {
                val text = tvExpression.text.toString()
                tvExpression.text = text.dropLast(1) + operator
            }
            hasOperator -> {
                Toast.makeText(this, "연산자는 한 번만 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return
            }
            else -> {
                tvExpression.append(" $operator")
            }
        }
        // 중간 텍스트 커스텀하기
        val ssb = SpannableStringBuilder(tvExpression.text)
        ssb.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this, R.color.green)),
            tvExpression.text.length - 1,
            tvExpression.text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        tvExpression.text = ssb

        isOperator = true
        hasOperator = true
    }

}