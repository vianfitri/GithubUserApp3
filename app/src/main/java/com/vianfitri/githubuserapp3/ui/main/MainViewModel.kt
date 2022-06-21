package com.vianfitri.githubuserapp3.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vianfitri.githubuserapp3.datasource.DetailResponse
import com.vianfitri.githubuserapp3.repository.UserRepository

class MainViewModel : ViewModel() {
    val usersDetail: LiveData<ArrayList<DetailResponse>> = UserRepository.usersDetail
    val isLoading: LiveData<Boolean> = UserRepository.isLoading

    init {
        UserRepository.getListUser()
    }
}