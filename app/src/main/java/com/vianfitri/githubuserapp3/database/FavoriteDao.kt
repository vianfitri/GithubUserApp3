package com.vianfitri.githubuserapp3.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(fav: FavoriteEntity)

    @Update
    fun update(fav: FavoriteEntity)

    @Delete
    fun delete(fav: FavoriteEntity)

    @Query("SELECT * from favorite WHERE id = :id")
    fun getUserFavoriteById(id: Int): LiveData<List<FavoriteEntity>>

    @Query("SELECT * from favorite WHERE login = :username")
    fun getUserFavoriteByLogin(username: String): LiveData<List<FavoriteEntity>>

    @Query("SELECT * from favorite ORDER BY id ASC")
    fun getAllFavorite(): LiveData<List<FavoriteEntity>>
}