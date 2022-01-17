package com.example.myfastcampusstudy_android.upper_intermediate.youtube.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myfastcampusstudy_android.databinding.ItemVideoBinding
import com.example.myfastcampusstudy_android.upper_intermediate.youtube.adapter.VideoAdapter.ViewHolder
import com.example.myfastcampusstudy_android.upper_intermediate.youtube.model.VideoModel

class VideoAdapter : ListAdapter<VideoModel, ViewHolder>(diffUtil) {
    inner class ViewHolder(var binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VideoModel) {
            binding.apply {
                tvTitle.text = item.title
                tvSubtitle.text = item.subtitle
                Glide.with(ivThumbnail.context)
                    .load(item.thumb)
                    .into(ivThumbnail)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemVideoBinding.inflate(
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
        val diffUtil = object : DiffUtil.ItemCallback<VideoModel>() {
            override fun areItemsTheSame(oldItem: VideoModel, newItem: VideoModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: VideoModel, newItem: VideoModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}