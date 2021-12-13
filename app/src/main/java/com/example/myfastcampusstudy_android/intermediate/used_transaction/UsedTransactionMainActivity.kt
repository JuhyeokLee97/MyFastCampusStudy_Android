package com.example.myfastcampusstudy_android.intermediate.used_transaction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.myfastcampusstudy_android.R
import com.example.myfastcampusstudy_android.intermediate.used_transaction.chat_list.ChatListFragment
import com.example.myfastcampusstudy_android.intermediate.used_transaction.home.HomeFragment
import com.example.myfastcampusstudy_android.intermediate.used_transaction.my_page.MyPageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class UsedTransactionMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_used_transaction_main)

        val homeFragment = HomeFragment()
        val chatListFragment = ChatListFragment()
        val myPageFragment = MyPageFragment()

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(homeFragment)

                R.id.chatList -> replaceFragment(chatListFragment)

                R.id.myPage -> replaceFragment(myPageFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            commit()
        }
    }
}