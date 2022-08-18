package com.vianfitri.githubuserapp3.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vianfitri.githubuserapp3.R
import com.vianfitri.githubuserapp3.databinding.ItemUserBinding
import com.vianfitri.githubuserapp3.datasource.DetailResponse
import com.vianfitri.githubuserapp3.ui.detail.DetailActivity

class UserAdapter(private val listUser: ArrayList<DetailResponse>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (username, avatar, _, _, _, name, _, _, bio) = listUser[position]
        holder.apply {
            with(binding) {
                Glide.with(itemView.context)
                    .load(avatar)
                    .placeholder(R.drawable.ic_baseline_photo_camera_24)
                    .error(R.drawable.ic_baseline_broken_image_24)
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
                itemView.setOnClickListener {
                     showDetail(itemView.context, position)
                }
            }
        }
    }

    private fun showDetail(context: Context, position: Int) {
        val detailIntent = Intent(context, DetailActivity::class.java)
        detailIntent.putExtra(DetailActivity.EXTRA_USER, listUser[position])
        context.startActivity(detailIntent)
    }
}