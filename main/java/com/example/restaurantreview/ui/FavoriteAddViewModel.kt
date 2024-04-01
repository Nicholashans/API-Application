package com.example.restaurantreview.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.restaurantreview.data.database.FavoriteUser
import com.example.restaurantreview.repository.FavoriteRepository

class FavoriteAddViewModel(application: Application) : ViewModel() {

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun insert(user: FavoriteUser) {
        mFavoriteRepository.insert(user)
    }

    fun delete(user: FavoriteUser) {
        mFavoriteRepository.delete(user)
    }

}