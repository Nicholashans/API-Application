package com.example.restaurantreview.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantreview.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private var _activityFavoriteBinding: ActivityFavoriteBinding? = null
    private val binding get() = _activityFavoriteBinding

    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        val favoriteViewModel = obtainViewModel(this@FavoriteActivity)
        favoriteViewModel.getAllFavorites().observe(this) {favoriteList ->
            if (favoriteList != null) {
                adapter.setListFavorites(favoriteList)
            }
        }

        adapter = FavoriteAdapter()

        binding?.rvFavorites?.layoutManager = LinearLayoutManager(this)
        binding?.rvFavorites?.setHasFixedSize(true)
        binding?.rvFavorites?.adapter = adapter



    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavoriteBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity) : FavoriteViewModel{
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity,factory).get(FavoriteViewModel::class.java)
    }

}