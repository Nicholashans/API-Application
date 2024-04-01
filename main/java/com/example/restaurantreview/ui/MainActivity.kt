package com.example.restaurantreview.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantreview.R
import com.example.restaurantreview.data.response.ItemsItem
import com.example.restaurantreview.databinding.ActivityMainBinding
import com.example.restaurantreview.ui.darkmode.DarkModeActivity
import com.example.restaurantreview.ui.darkmode.DarkModeViewModel
import com.example.restaurantreview.ui.darkmode.DarkModeViewModelFactory
import com.example.restaurantreview.ui.darkmode.SettingPreferences
import com.example.restaurantreview.ui.darkmode.dataStore
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private val darkModeViewModel: DarkModeViewModel by viewModels {
        DarkModeViewModelFactory(SettingPreferences.getInstance(applicationContext.dataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Thread.sleep(3000)
        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        val searchQuery = searchView.text.toString()
                        showLoading(true)
                        mainViewModel.findUser(searchQuery)
                        searchView.hide()
                        true
                    }
                    false
                }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvGithub.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvGithub.addItemDecoration(itemDecoration)
        binding.rvGithub.setHasFixedSize(true)

        mainViewModel.github.observe(this) {
            showUser(it)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.snackbarText.observe(this, {
            it.getContentIfNotHandled()?.let {snackBarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        })

        darkModeViewModel.getThemeSettings().observe(this) {isDark ->
            if (isDark) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.topAppBar.setOnMenuItemClickListener {menuItem ->
            when(menuItem.itemId) {
                R.id.fab_favorites -> {
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.btn_dark_mode -> {
                    val intentDark = Intent(this, DarkModeActivity::class.java)
                    startActivity(intentDark)
                    true

                }
                else -> false
            }

        }

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showUser(user: List<ItemsItem>){
        val adapter = SearchUserAdapter()
        adapter.submitList(user)
        binding.rvGithub.adapter = adapter
        binding.searchView.setText("")

    }


}