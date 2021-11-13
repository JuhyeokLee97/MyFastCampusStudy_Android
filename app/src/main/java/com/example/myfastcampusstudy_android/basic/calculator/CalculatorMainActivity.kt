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
import java.lang.NumberFormatException

class CalculatorMainActivity : AppCompatActivity() {
    private lateinit var tvExpression: TextView
    private lateinit var tvResult: TextView
    private var isOperator = false
    private var hasOperator = false

    private lateinit var historyLayout: View
    private lateinit var historyLinearLayout: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator_main)

        initView()
    }

    private fun initView() {
        tvExpression = findViewById(R.id.tvExpression)
        tvResult = findViewById(R.id.tvResult)
        historyLayout = findViewById(R.id.historyLayout)
        historyLinearLayout = findViewById(R.id.historyLinearLayout)
    }

    fun btnClicked(v: View) {
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
        tvResult.text = ""
        tvExpression.text = ""
        isOperator = false
        hasOperator = false
    }

    fun historyBtnClicked(v: View) {
        historyLayout.visibility = View.VISIBLE
        // TODO: 디비에서 모든 기록 가져오기
        // TODO: 뷰에 모든 기록 할당하기
    }

    fun closeHistoryBtnClicked(v: View) {
        historyLayout.visibility = View.GONE

    }

    fun historyClearBtnClicked(v: View) {
        // TODO 디비에서 모든 기록 삭제
        // TODO 뷰에서 모든 기록 삭제
    }

    fun resultBtnClicked(v: View) {
        val expressionTexts = tvExpression.text.split(" ")
        if (tvExpression.text.isEmpty() || expressionTexts.size == 1) {
            return
        }

        if (expressionTexts.size != 3 && hasOperator) { // "숫자" + "연산자"
            Toast.makeText(this, "아직 완성되지 않은 수식입니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if (expressionTexts[0].isNumber().not() || expressionTexts[2].isNumber().not()) {
            Toast.makeText(this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val expressionText = tvExpression.text.toString()
        val resultText = calculateExpression()

        tvResult.text = ""
        tvExpression.text = resultText

        isOperator = false
        hasOperator = false
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
        tvResult.text = calculateExpression()

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

    private fun calculateExpression(): String {
        var expressionTexts = tvExpression.text.split(" ")
        if (hasOperator.not() || expressionTexts.size != 3) {    // 예외처리
            return ""
        } else if (expressionTexts[0].isNumber().not() || expressionTexts[2].isNumber().not()) {
            return ""
        }
        val exp1 = expressionTexts[0].toBigInteger()
        val exp2 = expressionTexts[2].toBigInteger()
        val op = expressionTexts[1]
        return when (op) {
            "+" -> (exp1 + exp2).toString()
            "-" -> (exp1 - exp2).toString()
            "*" -> (exp1 * exp2).toString()
            "/" -> (exp1 / exp2).toString()
            "%" -> (exp1 % exp2).toString()
            else -> ""
        }
    }

    fun String.isNumber(): Boolean {
        return try {
            this.toBigInteger()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

}