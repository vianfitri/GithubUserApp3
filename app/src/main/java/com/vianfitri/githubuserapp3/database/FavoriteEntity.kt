package com.vianfitri.githubuserapp3.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity (tableName = "favorite")
@Parcelize
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "login")
    var login: String? = null,

    @ColumnInfo(name ="avatar_url")
    var avatarUrl: String? = null,

    @ColumnInfo(name = "url")
    var url: String? = null,

    @ColumnInfo(name ="followers_url")
    var followersUrl: String? = null,

    @ColumnInfo(name ="following_url")
    var followingUrl: String? = null,

    @ColumnInfo(name ="name")
    var name: String? = null,

    @ColumnInfo(name ="company")
    var company: String? = null,

    @ColumnInfo(name ="location")
    var location: String? = null,

    @ColumnInfo(name ="bio")
    var bio: String? = null,

    @ColumnInfo(name ="public_repos")
    var publicRepos: Int = 0,

    @ColumnInfo(name ="followers")
    var followers: Int = 0,

    @ColumnInfo(name ="following")
    var following: Int = 0
) : Parcelable