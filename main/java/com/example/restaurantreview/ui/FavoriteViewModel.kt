package com.example.restaurantreview.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantreview.data.database.FavoriteUser
import com.example.restaurantreview.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)
    fun getAllFavorites(): LiveData<List<FavoriteUser>> = mFavoriteRepository.getAllFavorites()
    fun isFavorite(name: String): LiveData<Boolean> = mFavoriteRepository.isFavorite(name)

}