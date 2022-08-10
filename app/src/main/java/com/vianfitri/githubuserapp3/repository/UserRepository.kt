package com.vianfitri.githubuserapp3.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.vianfitri.githubuserapp3.datasource.DetailResponse
import com.vianfitri.githubuserapp3.datasource.SearchResponse
import com.vianfitri.githubuserapp3.datasource.UsersResponse
import com.vianfitri.githubuserapp3.networking.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserRepository {
    var tmpUserList: ArrayList<DetailResponse> = ArrayList()
    val detailResponse = MutableLiveData<DetailResponse>()
    val searchResponse = MutableLiveData<SearchResponse>()
    val usersResponse = MutableLiveData<ArrayList<UsersResponse>>()
    val usersDetail = MutableLiveData<ArrayList<DetailResponse>>()
    val isLoading = MutableLiveData<Boolean>()
    private const val TAG = "UserRepository"

    fun getListUser() {
        tmpUserList.clear()
        isLoading.value = true
        val client = ApiConfig.getApiService().getUserList()
        client.enqueue(object : Callback<ArrayList<UsersResponse>> {
            override fun onResponse(
                call: Call<ArrayList<UsersResponse>>,
                response: Response<ArrayList<UsersResponse>>
            ) {
                if (response.isSuccessful) {
                    usersResponse.value = response.body()

                    for(users in usersResponse.value!!){
                        getDetailUser(users.login)
                    }
                    isLoading.value = false
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ArrayList<UsersResponse>>, t: Throwable) {
                isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getDetailUser(login: String) {
        isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(login)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                if (response.isSuccessful) {
                    val srcResponse = response.body()
                    if (srcResponse != null) {
                        tmpUserList.add(srcResponse)
                        usersDetail.value = tmpUserList
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getUserBySearch(user: String) {
        tmpUserList.clear()
        isLoading.value = true
        val client = ApiConfig.getApiService().getUserBySearch(user)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    searchResponse.value = response.body()

                    for(user in searchResponse.value?.items!!){
                        getDetailUser(user.login)
                    }
                    usersDetail.value = tmpUserList
                    isLoading.value = false
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}