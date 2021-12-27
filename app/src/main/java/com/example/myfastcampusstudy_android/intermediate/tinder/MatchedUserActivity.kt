package com.example.myfastcampusstudy_android.intermediate.tinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfastcampusstudy_android.databinding.ActivityMatchedUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MatchedUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMatchedUserBinding

    private var auth = FirebaseAuth.getInstance()
    private lateinit var usersDB: DatabaseReference
    private val matchedUserAdapter = MatchedUserAdapter()
    private val cardItems = mutableListOf<CardItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchedUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usersDB = Firebase.database.reference.child("Users")

        initMatchedUserRecyclerView()
        getMatchUsers()
    }

    private fun initMatchedUserRecyclerView() {
        binding.rvMatchedUser.apply {
            adapter = matchedUserAdapter
            layoutManager = LinearLayoutManager(this@MatchedUserActivity)
        }
    }

    private fun getMatchUsers() {
        val matchedUsersDB = usersDB.child(getCurrentUserId())
            .child("likedBy")
            .child("match")

        matchedUsersDB.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if (snapshot.key?.isNotEmpty() == true) {
                    getUserByKey(snapshot.key.orEmpty())
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun getUserByKey(userId: String) {
        usersDB.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            // 데이터가 있을 때 onDataChange 가 동작한다.
            override fun onDataChange(snapshot: DataSnapshot) {
                cardItems.add(CardItem(userId, snapshot.child("name").value.toString()))
                matchedUserAdapter.submitList(cardItems)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun getCurrentUserId(): String {
        if (auth.currentUser == null) {
            Toast.makeText(this, "로그인이 되어있지 않습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
        return auth.currentUser?.uid.orEmpty()
    }
}