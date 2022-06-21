package com.vianfitri.githubuserapp3.datasource

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("url")
    val url: String
)
