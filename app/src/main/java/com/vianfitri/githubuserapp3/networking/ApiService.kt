package com.vianfitri.githubuserapp3.networking

import com.vianfitri.githubuserapp3.BuildConfig
//import com.vianfitri.githubuserapp3.datasource.DetailResponse
//import com.vianfitri.githubuserapp3.datasource.SearchResponse
import com.vianfitri.githubuserapp3.datasource.UsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    @Headers("Authorization: token $API_TOKEN", "UserResponse-Agent: request")
    fun getUserList(): Call<ArrayList<UsersResponse>>

    /*
    @GET("users/{username}")
    @Headers("Authorization: token $API_TOKEN", "UserResponse-Agent: request")
    fun getDetailUser(@Path("username") username: String): Call<DetailResponse>

    @GET("search/users")
    @Headers("Authorization: token $API_TOKEN", "UserResponse-Agent: request")
    fun getUserBySearch(@Query("q") username: String): Call<SearchResponse>
    */

    @GET("users/{username}/followers")
    @Headers("Authorization: token $API_TOKEN", "UserResponse-Agent: request")
    fun getFollowersList(@Path("username") username: String): Call<ArrayList<UsersResponse>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $API_TOKEN", "UserResponse-Agent: request")
    fun getFollowingList(@Path("username") username: String): Call<ArrayList<UsersResponse>>

    companion object {
        private const val API_TOKEN = BuildConfig.API_TOKEN
    }
}