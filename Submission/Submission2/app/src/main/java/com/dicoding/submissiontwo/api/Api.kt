package com.dicoding.submissiontwo.api

import com.dicoding.submissiontwo.BuildConfig
import com.dicoding.submissiontwo.data.model.DetailUserResponse
import com.dicoding.submissiontwo.data.model.User
import com.dicoding.submissiontwo.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: token $key")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token $key")
    fun getUsersDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $key")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $key")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    companion object {
        const val key = BuildConfig.GITHUB_TOKEN
    }

}