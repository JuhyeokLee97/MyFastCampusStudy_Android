package com.example.myfastcampusstudy_android.basic.lottery_number_recommendation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.myfastcampusstudy_android.R

class LotteryNumberRecommendationMainActivity : AppCompatActivity() {
    private val btnClear: Button by lazy { findViewById(R.id.btnClearNumbers) }
    private val btnAddNumber: Button by lazy { findViewById(R.id.btnAddNumber) }
    private val btnRun: Button by lazy { findViewById(R.id.btnRun) }
    private val numberPicker: NumberPicker by lazy { findViewById(R.id.numberPicker) }
    private val tvNumberList: List<TextView> by lazy {
        listOf<TextView>(
            findViewById(R.id.tvFirstNumber),
            findViewById(R.id.tvSecondNumber),
            findViewById(R.id.tvThirdNumber),
            findViewById(R.id.tvFourthNumber),
            findViewById(R.id.tvFifthNumber),
            findViewById(R.id.tvSixthNumber)
        )
    }

    private var didRun = false
    private val pickNumberSet = hashSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottery_number_recommendation_main)

        initView()
    }

    private fun initView() {
        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initBtnRun()
        initBtnAddNumber()
    }

    private fun initBtnAddNumber() {
        btnAddNumber.setOnClickListener {
            if (didRun) {
                Toast.makeText(this, "초기화 후에 시도해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (pickNumberSet.size >= 6) {
                Toast.makeText(this, "번호는 6개까지만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (pickNumberSet.contains(numberPicker.value)) {
                Toast.makeText(this, "이미 선택한 번호입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val tvNumber = tvNumberList[pickNumberSet.size]
            tvNumber.text = numberPicker.value.toString()
            tvNumber.isVisible = true

            pickNumberSet.add(numberPicker.value)
        }
    }

    private fun initBtnRun() {
        btnRun.setOnClickListener {
            val list = getRandomNumber()
        }
    }

    private fun getRandomNumber(): List<Int> {
        val numberList = mutableListOf<Int>()
            .apply {
                for (i in 1..45)
                    this.add(i)
            }

        numberList.shuffle()
        return numberList.subList(0, 6).sorted()
    }
}