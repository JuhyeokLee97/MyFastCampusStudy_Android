package com.example.myfastcampusstudy_android.intermediate.used_transaction.chat_room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myfastcampusstudy_android.databinding.ActivityChatRoomBinding
import com.example.myfastcampusstudy_android.intermediate.DBKey
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatRoomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatRoomBinding

    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    private val chatList = mutableListOf<ChatModel>()
    private val adapter = ChatAdapter()
    private lateinit var chatDB: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        initViews()
    }

    private fun initData() {
        val chatKey = intent.getLongExtra("ChatKey", -1)
        chatDB = Firebase.database.reference.child(DBKey.DB_CHATS).child("$chatKey")

        chatDB.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatModel = snapshot.getValue(ChatModel::class.java)
                chatModel ?: return

                chatList.add(chatModel)
                adapter.submitList(chatList)
                adapter.notifyDataSetChanged()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun initViews() {
        initChatRecyclerView()
        initSendMessageButton()
    }

    private fun initChatRecyclerView() {
        binding.apply {
            rvChat.adapter = adapter
        }
    }

    private fun initSendMessageButton() {
        binding.apply {
            btnSendMessage.setOnClickListener {
                val chatModel = ChatModel(
                    senderId = auth.currentUser!!.uid,
                    message = etMessage.text.toString()
                )

                chatDB.push()?.setValue(chatModel)
            }
        }
    }
}