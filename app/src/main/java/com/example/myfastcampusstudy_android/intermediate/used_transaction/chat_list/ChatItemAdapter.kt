package com.example.myfastcampusstudy_android.intermediate.used_transaction.chat_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myfastcampusstudy_android.databinding.ItemChatListBinding
import com.google.firebase.auth.FirebaseAuth

class ChatItemAdapter(val onItemClicked: (ChatItemModel) -> Unit) :
    ListAdapter<ChatItemModel, ChatItemAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(val binding: ItemChatListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChatItemModel) {
            binding.apply {
                tvRoomTitle.text = item.itemTitle
                root.setOnClickListener {
                    onItemClicked(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemChatListBinding.inflate(
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
        val diffUtil = object : DiffUtil.ItemCallback<ChatItemModel>() {
            override fun areItemsTheSame(oldItem: ChatItemModel, newItem: ChatItemModel): Boolean {
                return oldItem.key == newItem.key
            }

            override fun areContentsTheSame(
                oldItem: ChatItemModel,
                newItem: ChatItemModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}