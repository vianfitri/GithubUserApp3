package com.vianfitri.githubuserapp3.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.vianfitri.githubuserapp3.datasource.DetailResponse
import com.vianfitri.githubuserapp3.networking.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

object UserRepository {
    val userSearch = MutableLiveData<ArrayList<DetailResponse>?>()
    val users = MutableLiveData<ArrayList<DetailResponse>?>()
    val isLoading = MutableLiveData<Boolean>()

    var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private const val TAG = "UserRepository"

    suspend fun getListUser() {
        coroutineScope.launch {
            isLoading.value = true
            val getUserDeferred = ApiConfig.getApiService().getUserList()
            try {
                users.value?.clear()
                for(user in getUserDeferred) {
                    val detail = ApiConfig.getApiService().getDetailUser(user.login)
                    users.value?.add(detail)
                }
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
                Log.e(TAG, "onFailure: ${e.message.toString()}")
            }
        }
    }

    fun getUserBySearch(user: String) {
        coroutineScope.launch {
            isLoading.value = true
            val getUserSearch = ApiConfig.getApiService().getUserBySearch(user)
            try {
                users.value?.clear()
                for(user in getUserSearch.items) {
                    val detail = ApiConfig.getApiService().getDetailUser(user.login)
                    users.value?.add(detail)
                }
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
                Log.e(TAG, "onFailure: ${e.message.toString()}")
            }
        }
    }

    fun searchUser(user: String){
        coroutineScope.launch {
            getUserBySearch(user)
        }
    }

    fun clearSearch(){
        coroutineScope.launch {
            getListUser()
        }
    }
}