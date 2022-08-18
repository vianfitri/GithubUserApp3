package com.vianfitri.githubuserapp3.ui.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vianfitri.githubuserapp3.database.FavoriteEntity
import com.vianfitri.githubuserapp3.repository.FavoriteRepository

class DetailViewModel(username: String, app: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(app)

    fun insert(favEntity: FavoriteEntity) {
        mFavoriteRepository.insert(favEntity)
    }

    fun delete(favEntity: FavoriteEntity) {
        mFavoriteRepository.delete(favEntity)
    }

    fun getFavoriteById(id: Int): LiveData<List<FavoriteEntity>> {
        return mFavoriteRepository.getUserFavoriteById(id)
    }

    fun getFavoriteByLogin(username: String) : LiveData<List<FavoriteEntity>> {
        return mFavoriteRepository.getUserFavoriteByLogin(username)
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}