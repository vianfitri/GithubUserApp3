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
            Glide.with(binding.root)
                .load(avatar)
                .into(binding.civAvatar)
            binding.tvProfileName.text = name
            binding.tvUsername.text = username
            binding.tvBio.text = bio

            if (name == "null") { binding.tvProfileName.visibility = View.GONE }
            else { binding.tvProfileName.visibility = View.VISIBLE }

            if (bio == "null") { binding.tvBio.visibility = View.GONE }
            else { binding.tvBio.visibility = View.VISIBLE }
        }
    }
}