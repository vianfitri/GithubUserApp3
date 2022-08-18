package com.vianfitri.githubuserapp3.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vianfitri.githubuserapp3.database.FavoriteEntity
import com.vianfitri.githubuserapp3.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavRepository: FavoriteRepository = FavoriteRepository(application)
    fun getAllFavorite(): LiveData<List<FavoriteEntity>> = mFavRepository.getAllFavorite()
}