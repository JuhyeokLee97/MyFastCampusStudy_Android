package com.example.myfastcampusstudy_android.intermediate.used_transaction.chat_room

import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myfastcampusstudy_android.databinding.ItemChatBinding
import com.google.firebase.auth.FirebaseAuth

class ChatAdapter(val auth: FirebaseAuth) :
    ListAdapter<ChatModel, ChatAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(var binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChatModel) {
            binding.apply {
                tvMessage.text = item.message
                tvSenderId.text = item.senderId.substring(0, 5)

                if (item.senderId != auth.currentUser!!.uid) {
                    tvMessage.gravity = Gravity.END
                    tvMessage.setBackgroundColor(Color.LTGRAY)
                    tvMessage.setTextColor(Color.BLACK)

                    tvSenderId.gravity = Gravity.END
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemChatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ChatModel>() {
            override fun areItemsTheSame(oldItem: ChatModel, newItem: ChatModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ChatModel,
                newItem: ChatModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}