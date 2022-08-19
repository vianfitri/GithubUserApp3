package com.vianfitri.githubuserapp3.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.vianfitri.githubuserapp3.datasource.DetailResponse
import com.vianfitri.githubuserapp3.repository.UserRepository
import com.vianfitri.githubuserapp3.ui.setting.SettingPreferences
import kotlinx.coroutines.launch

class MainViewModel(private val pref: SettingPreferences) : ViewModel() {
    val users: LiveData<ArrayList<DetailResponse>?> = UserRepository.users
    val searchUser: LiveData<ArrayList<DetailResponse>?> = UserRepository.userSearch
    val isLoading: LiveData<Boolean> = UserRepository.isLoading

    init {
        viewModelScope.launch {
            UserRepository.getListUser()
        }
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    override fun onCleared() {
        super.onCleared()
        UserRepository.viewModelJob.cancel()
    }
}