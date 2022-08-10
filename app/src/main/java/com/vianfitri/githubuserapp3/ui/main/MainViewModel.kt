package com.vianfitri.githubuserapp3.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.vianfitri.githubuserapp3.datasource.DetailResponse
import com.vianfitri.githubuserapp3.repository.UserRepository
import com.vianfitri.githubuserapp3.ui.setting.SettingPreferences

class MainViewModel(private val pref: SettingPreferences) : ViewModel() {
    val usersDetail: LiveData<ArrayList<DetailResponse>> = UserRepository.usersDetail
    val isLoading: LiveData<Boolean> = UserRepository.isLoading

    init {
        UserRepository.getListUser()
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }
}