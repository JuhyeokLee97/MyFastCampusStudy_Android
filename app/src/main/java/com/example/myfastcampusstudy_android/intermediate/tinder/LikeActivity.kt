package com.example.myfastcampusstudy_android.intermediate.tinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myfastcampusstudy_android.databinding.ActivityLikeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction

class LikeActivity : AppCompatActivity(), CardStackListener {
    private lateinit var binding: ActivityLikeBinding
    private var auth = FirebaseAuth.getInstance()
    private lateinit var usersDB: DatabaseReference
    private val adapter = CardItemAdapter()
    private val cardItems = mutableListOf<CardItem>()
    private val cardStackLayoutManager by lazy {
        CardStackLayoutManager(this, this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLikeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usersDB = Firebase.database.reference.child("Users")
        val currentUserDB = usersDB.child(getCurrentUserId())
        currentUserDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child("name").value == null) {
                    showNameInputPopup()
                    return
                }
                initUserName(snapshot.child("name").value.toString())
                getUnSelectedUsers()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        initCardStackView()
        initSignOutButton()
        initMatchedListButton()
    }

    private fun initSignOutButton() {
        binding.btnSignOut.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this@LikeActivity, TinderMainActivity::class.java))
            finish()
        }
    }

    private fun initMatchedListButton() {
        binding.btnShowMatchedList.setOnClickListener {
            startActivity(Intent(this@LikeActivity, MatchedUserActivity::class.java))
        }
    }

    private fun initUserName(name: String) {
        binding.tvName.text = name
    }

    private fun initCardStackView() {
        binding.cardStackView.apply {
            layoutManager = cardStackLayoutManager
            adapter = this@LikeActivity.adapter
        }
    }

    private fun getUnSelectedUsers() {
        usersDB.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                with(snapshot) {
                    if (child("userId").value != getCurrentUserId()
                        && child("likedBy").child("like").hasChild(getCurrentUserId()).not()
                        && child("likedBy").child("disLike").hasChild(getCurrentUserId()).not()
                    ) {

                        val userId = child("userId").value.toString()
                        var name = "undecided"
                        if (child("name").value != null) {
                            name = child("name").value.toString()
                        }

                        cardItems.add(CardItem(userId, name))
                        adapter.submitList(cardItems)
                        adapter.notifyDataSetChanged()
                    }
                }

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                cardItems.find { it.userId == snapshot.child("userId").value.toString() }?.let {
                    it.name = snapshot.child("name").value.toString()
                }
                adapter.submitList(cardItems)
                adapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

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

    private fun showNameInputPopup() {
        var editText = EditText(this)
        AlertDialog.Builder(this)
            .setTitle("이름을 입력해주세요")
            .setView(editText)
            .setPositiveButton("확인") { _, _ ->
                if (editText.text.isEmpty()) {
                    showNameInputPopup()
                } else {
                    saveUserName(editText.text.toString())
                }


            }
            .setCancelable(false)
            .show()
    }

    private fun saveUserName(name: String) {
        val userId = getCurrentUserId()
        val user = mutableMapOf<String, Any>()
        user["userId"] = userId
        user["name"] = name
        usersDB.child(userId).updateChildren(user)

        // getUnSelectedUsers() 함수가 usersDB에 리스너를 다는 것인데 굳이 여기에 있어야 하나?
        // 함수의 위치를 바꿔가며 정상 작동 하는지 알아보자.
        //   => 데이터 동기화가 느리지만 정상 작동한다.
        getUnSelectedUsers()
    }

    private fun like() {
        val card = cardItems[cardStackLayoutManager.topPosition - 1]
        cardItems.removeFirst()

        usersDB.child(card.userId)
            .child("likedBy")
            .child("like")
            .child(getCurrentUserId())
            .setValue(true)

        saveMatchIfOtherUserLikedMe(card.userId)
        Toast.makeText(this, "${card.name}님을 Like 하셨습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun disLike() {
        val card = cardItems[cardStackLayoutManager.topPosition - 1]
        cardItems.removeFirst()

        usersDB.child(card.userId)
            .child("likedBy")
            .child("disLike")
            .child(getCurrentUserId())
            .setValue(true)


        Toast.makeText(this, "${card.name}님을 disLike 하셨습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun saveMatchIfOtherUserLikedMe(otherUserId: String) {
        val isOtherUserLikeMe = usersDB.child(getCurrentUserId()).child("likedBy")
            .child("like")
            .child(otherUserId)

        isOtherUserLikeMe.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value == true) {
                    usersDB.child(getCurrentUserId())
                        .child("likedBy")
                        .child("match")
                        .child(otherUserId)
                        .setValue(true)

                    usersDB.child(otherUserId)
                        .child("likedBy")
                        .child("match")
                        .child(getCurrentUserId())
                        .setValue(true)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })

    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {}

    override fun onCardSwiped(direction: Direction?) {
        when (direction) {
            Direction.Right -> like()
            Direction.Left -> disLike()
            else -> {

            }
        }
    }

    override fun onCardRewound() {}

    override fun onCardCanceled() {}

    override fun onCardAppeared(view: View?, position: Int) {}

    override fun onCardDisappeared(view: View?, position: Int) {}
}