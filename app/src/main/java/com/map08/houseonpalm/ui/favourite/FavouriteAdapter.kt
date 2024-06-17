package com.map08.houseonpalm.ui.favourite

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
import com.map08.houseonpalm.models.Favourite

class FavouriteAdapter(
    private val onFavouriteClicked: (Favourite) -> Unit
) : ListAdapter<Favourite, FavouriteAdapter.FavouriteViewHolder>(FavouriteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favourite, parent, false)
        return FavouriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val favourite = getItem(position)
        holder.bind(favourite)
        holder.itemView.setOnClickListener { onFavouriteClicked(favourite) }
    }

    class FavouriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val addressTextView: TextView = itemView.findViewById(R.id.addressTextView)
        private val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
        private val favouriteImageView: ImageView = itemView.findViewById(R.id.favouriteImageView)

        fun bind(favourite: Favourite) {
            addressTextView.text = favourite.address
            priceTextView.text = favourite.price.toString()
            // 使用 ImageDownloaderTask 加载图像
            ImageDownloaderTask(favouriteImageView).execute(favourite.imageUrl)
        }
    }

    class FavouriteDiffCallback : DiffUtil.ItemCallback<Favourite>() {
        override fun areItemsTheSame(oldItem: Favourite, newItem: Favourite): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Favourite, newItem: Favourite): Boolean {
            return oldItem == newItem
        }
    }
}
