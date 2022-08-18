package com.vianfitri.githubuserapp3.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.vianfitri.githubuserapp3.database.FavoriteDao
import com.vianfitri.githubuserapp3.database.FavoriteEntity
import com.vianfitri.githubuserapp3.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavDao = db.favDao()
    }

    fun getAllFavorite(): LiveData<List<FavoriteEntity>> = mFavDao.getAllFavorite()

    fun getUserFavoriteById(id: Int): LiveData<List<FavoriteEntity>> = mFavDao.getUserFavoriteById(id)

    fun getUserFavoriteByLogin(username: String): LiveData<List<FavoriteEntity>> = mFavDao.getUserFavoriteByLogin(username)

    fun insert(fav: FavoriteEntity) {
        executorService.execute { mFavDao.insert(fav) }
    }
    fun delete(fav: FavoriteEntity) {
        executorService.execute { mFavDao.delete(fav) }
    }
    fun update(fav: FavoriteEntity) {
        executorService.execute { mFavDao.update(fav) }
    }
}