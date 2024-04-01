package com.example.restaurantreview.data.retrofit

import com.example.restaurantreview.BuildConfig
import com.example.restaurantreview.data.response.DetailUserResponse
import com.example.restaurantreview.data.response.GithubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    @GET("search/users")
    fun getUser(
       @Query("q") username: String
    ): Call<GithubResponse>

    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<DetailUserResponse>>

    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<DetailUserResponse>>
}