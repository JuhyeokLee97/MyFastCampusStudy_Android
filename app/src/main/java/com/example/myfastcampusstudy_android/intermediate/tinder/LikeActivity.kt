package com.example.myfastcampusstudy_android.intermediate.tinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myfastcampusstudy_android.databinding.ActivityLikeBinding
import com.example.myfastcampusstudy_android.intermediate.DBKey.Companion.DISLIKE
import com.example.myfastcampusstudy_android.intermediate.DBKey.Companion.LIKE
import com.example.myfastcampusstudy_android.intermediate.DBKey.Companion.LIKED_BY
import com.example.myfastcampusstudy_android.intermediate.DBKey.Companion.MATCH
import com.example.myfastcampusstudy_android.intermediate.DBKey.Companion.NAME
import com.example.myfastcampusstudy_android.intermediate.DBKey.Companion.USERS
import com.example.myfastcampusstudy_android.intermediate.DBKey.Companion.USER_ID
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

        usersDB = Firebase.database.reference.child(USERS)
        val currentUserDB = usersDB.child(getCurrentUserId())
        currentUserDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child(NAME).value == null) {
                    showNameInputPopup()
                    return
                }
                initUserName(snapshot.child(NAME).value.toString())
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
                    if (child(USER_ID).value != getCurrentUserId()
                        && child(LIKED_BY).child(LIKE).hasChild(getCurrentUserId()).not()
                        && child(LIKED_BY).child(DISLIKE).hasChild(getCurrentUserId()).not()
                    ) {

                        val userId = child(USER_ID).value.toString()
                        var name = "undecided"
                        if (child(NAME).value != null) {
                            name = child(NAME).value.toString()
                        }

                        cardItems.add(CardItem(userId, name))
                        adapter.submitList(cardItems)
                        adapter.notifyDataSetChanged()
                    }
                }

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                cardItems.find { it.userId == snapshot.child(USER_ID).value.toString() }?.let {
                    it.name = snapshot.child(NAME).value.toString()
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
            Toast.makeText(this, "???????????? ???????????? ????????????.", Toast.LENGTH_SHORT).show()
            finish()
        }
        return auth.currentUser?.uid.orEmpty()
    }

    private fun showNameInputPopup() {
        var editText = EditText(this)
        AlertDialog.Builder(this)
            .setTitle("????????? ??????????????????")
            .setView(editText)
            .setPositiveButton("??????") { _, _ ->
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
        user[USER_ID] = userId
        user[NAME] = name
        usersDB.child(userId).updateChildren(user)

        // getUnSelectedUsers() ????????? usersDB??? ???????????? ?????? ????????? ?????? ????????? ????????? ???????
        // ????????? ????????? ???????????? ?????? ?????? ????????? ????????????.
        //   => ????????? ???????????? ???????????? ?????? ????????????.
        getUnSelectedUsers()
    }

    private fun like() {
        val card = cardItems[cardStackLayoutManager.topPosition - 1]
        cardItems.removeFirst()

        usersDB.child(card.userId)
            .child(LIKED_BY)
            .child(LIKE)
            .child(getCurrentUserId())
            .setValue(true)

        saveMatchIfOtherUserLikedMe(card.userId)
        Toast.makeText(this, "${card.name}?????? Like ???????????????.", Toast.LENGTH_SHORT).show()
    }

    private fun disLike() {
        val card = cardItems[cardStackLayoutManager.topPosition - 1]
        cardItems.removeFirst()

        usersDB.child(card.userId)
            .child(LIKED_BY)
            .child(DISLIKE)
            .child(getCurrentUserId())
            .setValue(true)


        Toast.makeText(this, "${card.name}?????? disLike ???????????????.", Toast.LENGTH_SHORT).show()
    }

    private fun saveMatchIfOtherUserLikedMe(otherUserId: String) {
        val isOtherUserLikeMe = usersDB.child(getCurrentUserId()).child(LIKED_BY)
            .child(LIKE)
            .child(otherUserId)

        isOtherUserLikeMe.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value == true) {
                    usersDB.child(getCurrentUserId())
                        .child(LIKED_BY)
                        .child(MATCH)
                        .child(otherUserId)
                        .setValue(true)

                    usersDB.child(otherUserId)
                        .child(LIKED_BY)
                        .child(MATCH)
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