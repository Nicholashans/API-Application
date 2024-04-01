package com.example.restaurantreview.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantreview.data.response.DetailUserResponse
import com.example.restaurantreview.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel : ViewModel() {
    private val _userDetail = MutableLiveData<DetailUserResponse>()
    val userDetail: LiveData<DetailUserResponse> = _userDetail

    private val _userNumberFollowers = MutableLiveData<Int>()
    val userNumberFollowers: LiveData<Int> = _userNumberFollowers

    private val _userNumberFollowing = MutableLiveData<Int>()
    val userNumberFollowing: LiveData<Int> = _userNumberFollowing

    private val _userNameDetail = MutableLiveData<String?>()
    val userNameDetail: LiveData<String?> = _userNameDetail

    private val _userFollowers = MutableLiveData<List<DetailUserResponse>>()
    val userFollowers: LiveData<List<DetailUserResponse>> = _userFollowers

    private val _userFollowing = MutableLiveData<List<DetailUserResponse>>()
    val userFollowing: LiveData<List<DetailUserResponse>> = _userFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isLoadingDetail = MutableLiveData<Boolean>()
    val isLoadingDetail: LiveData<Boolean> = _isLoadingDetail

    private val _isLoadingFollowers = MutableLiveData<Boolean>()
    val isLoadingFollowers: LiveData<Boolean> = _isLoadingFollowers

    private val _isLoadingFollowing = MutableLiveData<Boolean>()
    val isLoadingFollowing: LiveData<Boolean> = _isLoadingFollowing


    fun findUserDetail(username: String) {
        _isLoadingDetail.value = true
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoadingDetail.value = false
                if (response.isSuccessful) {
                    _userDetail.value = response.body()
                    _userNameDetail.value = response.body()?.name
                    _userNumberFollowers.value = response.body()?.followers ?: 0
                    _userNumberFollowing.value = response.body()?.following ?: 0
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoadingDetail.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun findUserFollowers(username: String) {
        _isLoadingFollowers.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<DetailUserResponse>> {
            override fun onResponse(
                call: Call<List<DetailUserResponse>>,
                response: Response<List<DetailUserResponse>>
            ) {
                _isLoadingFollowers.value = false
                if (response.isSuccessful) {
                    _userFollowers.value = response.body()

                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<DetailUserResponse>>, t: Throwable) {
                _isLoadingFollowers.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun findUserFollowing(username: String) {
        _isLoadingFollowing.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<DetailUserResponse>> {
            override fun onResponse(
                call: Call<List<DetailUserResponse>>,
                response: Response<List<DetailUserResponse>>
            ) {
                _isLoadingFollowing.value = false
                if (response.isSuccessful) {
                    _userFollowing.value = response.body()

                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<DetailUserResponse>>, t: Throwable) {
                _isLoadingFollowing.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "DetailUserViewModel"
    }
}