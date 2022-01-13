package com.example.myfastcampusstudy_android.intermediate.used_transaction.chat_room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myfastcampusstudy_android.databinding.ItemChatBinding

class ChatAdapter : ListAdapter<ChatModel, ChatAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(var binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChatModel) {
            binding.apply {
                tvMessage.text = item.message
                tvSenderId.text = item.senderId
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