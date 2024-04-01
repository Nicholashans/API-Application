package com.example.restaurantreview.ui.darkmode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DarkModeViewModelFactory(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DarkModeViewModel::class.java)) {
            return DarkModeViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTANCE: DarkModeViewModelFactory? = null

        @JvmStatic
        fun getInstance(pref: SettingPreferences): DarkModeViewModelFactory {
            if (INSTANCE == null) {
                synchronized(DarkModeViewModelFactory::class.java) {
                    INSTANCE = DarkModeViewModelFactory(pref)
                }
            }
            return INSTANCE as DarkModeViewModelFactory
        }
    }

}