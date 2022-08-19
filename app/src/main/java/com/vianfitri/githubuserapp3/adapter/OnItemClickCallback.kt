package com.vianfitri.githubuserapp3.adapter

import com.vianfitri.githubuserapp3.datasource.DetailResponse

interface OnItemClickCallback {
    fun onItemClicked(user: DetailResponse)
}