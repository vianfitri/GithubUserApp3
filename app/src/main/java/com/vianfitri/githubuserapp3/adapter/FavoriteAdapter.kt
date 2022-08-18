package com.vianfitri.githubuserapp3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vianfitri.githubuserapp3.R
import com.vianfitri.githubuserapp3.database.FavoriteEntity
import de.hdodenhof.circleimageview.CircleImageView

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    private val userFavorites = ArrayList<FavoriteEntity>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var civAvatar: CircleImageView = itemView.findViewById(R.id.civ_avatar)
        var tvProfileName: TextView = itemView.findViewById(R.id.tv_profile_name)
        var tvUsername: TextView = itemView.findViewById(R.id.tv_username)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (_, login, name, avatar_url) = userFavorites[position]

        holder.apply {
            Glide.with(itemView.context)
                .load(avatar_url)
                .into(civAvatar)
            tvProfileName.text = name
            tvUsername.text = login

            if (name == "null") { tvProfileName.visibility = View.GONE } else {
                tvProfileName.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount() = userFavorites.size

    fun setListFavorite(items: List<FavoriteEntity>) {
        userFavorites.clear()
        userFavorites.addAll(items)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(favEntity: FavoriteEntity)
    }
}