package com.vianfitri.githubuserapp3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vianfitri.githubuserapp3.R
import com.vianfitri.githubuserapp3.datasource.DetailResponse
import de.hdodenhof.circleimageview.CircleImageView

class FollowersAdapter(private val listUser: ArrayList<DetailResponse>):
    RecyclerView.Adapter<FollowersAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var civAvatar: CircleImageView = itemView.findViewById(R.id.civ_avatar)
        var tvProfileName: TextView = itemView.findViewById(R.id.tv_profile_name)
        var tvUsername: TextView = itemView.findViewById(R.id.tv_username)
        var tvBio: TextView = itemView.findViewById(R.id.tv_bio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val datafollowers = listUser[position]

        holder.apply {
            Glide.with(itemView.context)
                .load(datafollowers.avatarUrl)
                .into(civAvatar)
            tvProfileName.text = datafollowers.name
            tvUsername.text = datafollowers.login
            tvBio.text = datafollowers.bio

            if (datafollowers.name == "null") { tvProfileName.visibility = View.GONE } else {
                tvProfileName.visibility = View.VISIBLE
            }

            if (datafollowers.bio == "null") { tvBio.visibility = View.GONE } else {
                tvBio.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount() = listUser.size
}