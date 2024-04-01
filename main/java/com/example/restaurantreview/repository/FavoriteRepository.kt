package com.example.restaurantreview.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.restaurantreview.data.database.FavoriteDao
import com.example.restaurantreview.data.database.FavoriteDatabase
import com.example.restaurantreview.data.database.FavoriteUser
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteDatabase.getDatabase(application)
        mFavoriteDao = db.FavoriteDao()
    }

    fun getAllFavorites(): LiveData<List<FavoriteUser>> = mFavoriteDao.getAllFavorites()

    fun insert(user: FavoriteUser) {
        executorService.execute { mFavoriteDao.insert(user) }
    }

    fun delete(user: FavoriteUser) {
        executorService.execute { mFavoriteDao.delete(user) }
    }

    fun isFavorite(name: String) : LiveData<Boolean> = mFavoriteDao.isFavorite(name)

    fun getFavoriteByUsername(name: String): LiveData<FavoriteUser> = mFavoriteDao.getFavoriteUserByUsername(name)


}