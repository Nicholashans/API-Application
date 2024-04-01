package com.example.restaurantreview.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.restaurantreview.R
import com.example.restaurantreview.data.database.FavoriteUser
import com.example.restaurantreview.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.content_followers ,
            R.string.content_following
        )
        const val EXTRA_FAVORITE = "extra_favorite"
    }

    private var favoriteUser: FavoriteUser? = null
    private lateinit var favoriteAddViewModel: FavoriteAddViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailUserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteViewModel = obtainFavoriteViewModel(this@DetailActivity)
        favoriteAddViewModel = obtainViewModel(this@DetailActivity)
        val viewPager: ViewPager2 = binding.viewPager
        val username = intent.getStringExtra("username")

        val followPagerAdapter = FollowPagerAdapter(this, username)
        viewPager.adapter = followPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        if (username != null) {
            detailViewModel.findUserDetail(username)
        }
        val avatarUrl = intent.getStringExtra("avatarUrl")
        val description = intent.getStringExtra("description")

        binding.userDetailName.text = username
        Glide.with(this)
            .load(avatarUrl)
            .circleCrop()
            .into(binding.ivDetail)
        binding.userDetailDescription.text = description

        detailViewModel.userDetail.observe(this, { user ->
            binding.userDetailName.text = user.login
        })

        var userSpecificName = binding.userDetailName.text.toString()

        username.let {
            if (it != null) {
                detailViewModel.findUserFollowers(it)
            }
            if (it != null) {
                detailViewModel.findUserFollowing(it)
            }
        }

        detailViewModel.userNumberFollowers.observe(this, { followersCount ->
            binding.userDetailNumberFollowers.text = followersCount.toString()
        })

        detailViewModel.userNumberFollowing.observe(this, { followingCount ->
            binding.userDetailNumberFollowing.text = followingCount.toString()
        })

        detailViewModel.userNameDetail.observe(this, {userNameDetail ->
            binding.userDetailDescription.text = userNameDetail.toString()

        })

        detailViewModel.isLoadingDetail.observe(this) {
            showLoadingDetail(it)
        }

        detailViewModel.isLoadingFollowers.observe(this) {
            showLoadingFollowers(it)
        }

        detailViewModel.isLoadingFollowing.observe(this) {
            showLoadingFollowing(it)
        }

        favoriteUser = intent.getParcelableExtra(EXTRA_FAVORITE)
        favoriteUser = FavoriteUser();
            favoriteViewModel.isFavorite(userSpecificName).observe(this){ result ->
                if (result) {
                    binding.fabFavorites.hide()
                    binding.fabRemoveFavorites.show()
                    binding.fabFavorites.isEnabled = false
                    binding.fabRemoveFavorites.isEnabled = true
                }  else {
                    binding.fabRemoveFavorites.hide()
                    binding.fabFavorites.show()
                    binding.fabRemoveFavorites.isEnabled = false
                    binding.fabFavorites.isEnabled = true

                }
            }

        binding?.fabFavorites?.setOnClickListener {
            val username = binding.userDetailName.text.toString()

            favoriteUser?.let { favoriteUser ->
                favoriteUser.username = username
                favoriteUser.avatarUrl = avatarUrl
                favoriteAddViewModel.insert(favoriteUser as FavoriteUser)
                showToast(getString(R.string.added))
            }
            binding.fabFavorites.hide()
            binding.fabRemoveFavorites.show()
            binding.fabFavorites.isEnabled = false
            binding.fabRemoveFavorites.isEnabled = true
        }

        binding.fabRemoveFavorites.setOnClickListener {
            val username = binding.userDetailName.text.toString()
            favoriteUser?.let { favoriteUser ->
                favoriteUser.username = username
                favoriteUser.avatarUrl = avatarUrl
                favoriteAddViewModel.delete(favoriteUser)
                showToast(getString(R.string.deleted))
            }
            binding.fabRemoveFavorites.hide()
            binding.fabRemoveFavorites.isEnabled = false
            binding.fabFavorites.isEnabled = true
            binding.fabFavorites.show()

        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoadingDetail(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showLoadingFollowers(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showLoadingFollowing(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteAddViewModel{
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity,factory).get(FavoriteAddViewModel::class.java)
    }

    private fun obtainFavoriteViewModel(activity: AppCompatActivity): FavoriteViewModel{
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity,factory).get(FavoriteViewModel::class.java)
    }

}