package com.example.restaurantreview.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurantreview.data.database.FavoriteUser
import com.example.restaurantreview.databinding.ItemUserFavoriteBinding
import com.example.restaurantreview.helper.FavoriteDiffCallback

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    private val listFavorites = ArrayList<FavoriteUser>()
    fun setListFavorites(listFavorites: List<FavoriteUser>) {
        val diffCallback = FavoriteDiffCallback(this.listFavorites, listFavorites)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorites.clear()
        this.listFavorites.addAll(listFavorites)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class FavoriteViewHolder (private val binding: ItemUserFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteUser: FavoriteUser){
            with(binding) {
                tvItem.text = favoriteUser.username
                Glide.with(binding.root)
                    .load(favoriteUser.avatarUrl)
                    .circleCrop()
                    .into(binding.imgItemPhoto)

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemUserFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listFavorites.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorites[position])

        val user = getItem(position)
        holder.bind(user)


        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Kamu memilih " + getItem(holder.adapterPosition).username, Toast.LENGTH_SHORT).show()

            val user = getItem(holder.adapterPosition)
            val username = user.username
            val avatarUrl = user.avatarUrl

            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra("username", username)
            intentDetail.putExtra("avatarUrl", avatarUrl)

            holder.itemView.context.startActivity(intentDetail)
        }


    }

    fun getItem(position: Int): FavoriteUser {
        return listFavorites[position]
    }
}