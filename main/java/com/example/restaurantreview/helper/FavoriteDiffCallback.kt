package com.example.restaurantreview.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.restaurantreview.data.database.FavoriteUser

class FavoriteDiffCallback(private val oldFavoriteList: List<FavoriteUser>, private val newFavoriteList: List<FavoriteUser>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavoriteList.size

    override fun getNewListSize(): Int = newFavoriteList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavoriteList[oldItemPosition].username == newFavoriteList[newItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavoriteUser = oldFavoriteList[oldItemPosition]
        val newFavoriteUser = newFavoriteList[newItemPosition]
        return oldFavoriteUser.username == newFavoriteUser.username && oldFavoriteUser.avatarUrl == newFavoriteUser.avatarUrl
    }
}