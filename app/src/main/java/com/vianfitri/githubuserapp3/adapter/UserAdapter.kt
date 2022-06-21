package com.vianfitri.githubuserapp3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vianfitri.githubuserapp3.databinding.ItemUserBinding
import com.vianfitri.githubuserapp3.datasource.DetailResponse

class UserAdapter(private val listUser: ArrayList<DetailResponse>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (username, name, avatar, _, _, _, _, _, bio) = listUser[position]
        holder.apply {
            with(binding) {
                Glide.with(root.context)
                    .load(avatar)
                    .into(civAvatar)
                tvProfileName.text = name
                tvUsername.text = username
                tvBio.text = bio

                if (name == "null") {
                    tvProfileName.visibility = View.GONE
                } else {
                    tvProfileName.visibility = View.VISIBLE
                }

                if (bio == "null") {
                    tvBio.visibility = View.GONE
                } else {
                    tvBio.visibility = View.VISIBLE
                }
            }
        }
    }
}