package com.vianfitri.githubuserapp3.ui.follows

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vianfitri.githubuserapp3.datasource.DetailResponse
import com.vianfitri.githubuserapp3.networking.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FollowsViewModel(username: String) : ViewModel() {
    private val _followers = MutableLiveData<ArrayList<DetailResponse>?>()
    val followers: LiveData<ArrayList<DetailResponse>?> = _followers
    private val _following = MutableLiveData<ArrayList<DetailResponse>?>()
    val following: LiveData<ArrayList<DetailResponse>?> = _following
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isDataFailed = MutableLiveData<Boolean>()
    val isDataFailed: LiveData<Boolean> = _isDataFailed

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        viewModelScope.launch {
            getFollowersList(username)
            getFollowingList(username)
        }
        Log.i(TAG, "FollFragment is Created")
    }

    private suspend fun getFollowersList(username: String) {
        coroutineScope.launch {
            _isLoading.value = true
            var result = ApiConfig.getApiService().getFollowersList(username)
            try{
                _isLoading.value = false
                //_followers.postValue(result)
            }catch (e: Exception){
                _isLoading.value = false
                _isDataFailed.value = true
                Log.e(TAG, "OnFailure: ${e.message.toString()}")
            }
        }
    }

    private suspend fun getFollowingList(username: String){
        coroutineScope.launch {
            _isLoading.value = true
            var result = ApiConfig.getApiService().getFollowingList(username)
            try{
                _isLoading.value = false
                //_following.postValue(result)
            }catch (e: Exception){
                _isLoading.value = false
                _isDataFailed.value = true
                Log.e(TAG, "OnFailure: ${e.message.toString()}")
            }
        }
    }

    companion object {
        private const val TAG = "FollowsViewModel"
    }
}
