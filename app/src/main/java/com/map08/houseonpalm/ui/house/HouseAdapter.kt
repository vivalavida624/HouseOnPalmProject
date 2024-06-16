package com.map08.houseonpalm.ui.house

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.map08.houseonpalm.ImageDownloaderTask
import com.map08.houseonpalm.R
import com.map08.houseonpalm.models.House

class HouseAdapter(
    private val onHouseClicked: (House) -> Unit
) : ListAdapter<House, HouseAdapter.HouseViewHolder>(HouseDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_house, parent, false)
        return HouseViewHolder(view)
    }

    override fun onBindViewHolder(holder: HouseViewHolder, position: Int) {
        val house = getItem(position)
        holder.bind(house)
        holder.itemView.setOnClickListener { onHouseClicked(house) }
    }

    class HouseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val addressTextView: TextView = itemView.findViewById(R.id.addressTextView)
        private val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
        private val houseImageView: ImageView = itemView.findViewById(R.id.houseImageView)

        fun bind(house: House) {
            addressTextView.text = house.address
            priceTextView.text = house.price.toString()
            // 使用 ImageDownloaderTask 加载图像
            ImageDownloaderTask(houseImageView).execute(house.imageUrl)
        }
    }

    class HouseDiffCallback : DiffUtil.ItemCallback<House>() {
        override fun areItemsTheSame(oldItem: House, newItem: House): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: House, newItem: House): Boolean {
            return oldItem == newItem
        }
    }
}