package com.example.myfastcampusstudy_android.intermediate.used_transaction.chat_list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.myfastcampusstudy_android.R
import com.example.myfastcampusstudy_android.databinding.FragmentChatListBinding
import com.example.myfastcampusstudy_android.intermediate.DBKey
import com.example.myfastcampusstudy_android.intermediate.used_transaction.chat_room.ChatRoomActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatListFragment : Fragment(R.layout.fragment_chat_list) {

    private lateinit var binding: FragmentChatListBinding
    private lateinit var chatItemAdapter: ChatItemAdapter
    private lateinit var chatDB: DatabaseReference

    private val chatRoomList = mutableListOf<ChatItemModel>()


    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentChatListBinding.bind(view)
        initViews()


    }

    private fun initViews() {
        initChatItemRecyclerView()
    }

    private fun initChatItemRecyclerView() {
        if (!isActiveUser()) {
            Snackbar.make(
                binding.root,
                "로그인 후, 사용해주세요.",
                Snackbar.LENGTH_SHORT
            ).show()
            return
        }

        chatItemAdapter = ChatItemAdapter(onItemClicked = { chatItemModel ->
            val intent = Intent(requireContext(), ChatRoomActivity::class.java)
            intent.putExtra("chatKey", chatItemModel.key)
            startActivity(intent)
        })
        binding.rvChatItem.adapter = chatItemAdapter

        chatDB = Firebase.database.reference.child(DBKey.DB_USERS).child(auth.currentUser!!.uid)
            .child(DBKey.CHILD_CHAT)
        chatDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val model = it.getValue(ChatItemModel::class.java)
                    model ?: return
                    chatRoomList.add(model)
                }

                chatItemAdapter.submitList(chatRoomList)
                chatItemAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


    }

    private fun isActiveUser(): Boolean = auth.currentUser != null

    override fun onResume() {
        super.onResume()
        chatItemAdapter.notifyDataSetChanged()
    }
}