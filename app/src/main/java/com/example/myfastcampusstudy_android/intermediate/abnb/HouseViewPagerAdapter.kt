package com.example.myfastcampusstudy_android.intermediate.abnb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myfastcampusstudy_android.R

class HouseViewPagerAdapter(val itemClicked: (HouseModel) -> Unit) :
    androidx.recyclerview.widget.ListAdapter<HouseModel, HouseViewPagerAdapter.ViewHolder>(differ) {

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(houseModel: HouseModel) {
            val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
            val tvPrice = view.findViewById<TextView>(R.id.tvPrice)
            val ivThumbnail = view.findViewById<ImageView>(R.id.ivThumbnail)

            tvTitle.text = houseModel.title
            tvPrice.text = houseModel.price

            Glide.with(ivThumbnail.context)
                .load(houseModel.imgUrl)
                .into(ivThumbnail)

            view.setOnClickListener {
                itemClicked(houseModel)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_house_detail_for_viewpager, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val differ = object : DiffUtil.ItemCallback<HouseModel>() {
            override fun areItemsTheSame(oldItem: HouseModel, newItem: HouseModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: HouseModel, newItem: HouseModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}