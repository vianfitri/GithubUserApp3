package com.vianfitri.githubuserapp3.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vianfitri.githubuserapp3.R
import com.vianfitri.githubuserapp3.databinding.ItemUserBinding
import com.vianfitri.githubuserapp3.datasource.DetailResponse
import com.vianfitri.githubuserapp3.ui.detail.DetailActivity

class FollowsAdapter: RecyclerView.Adapter<FollowsAdapter.ViewHolder>() {
    private var listUserResponse = ArrayList<DetailResponse>()

    fun addDataToList(items: ArrayList<DetailResponse>) {
        listUserResponse.clear()
        listUserResponse.addAll(items)
    }

    class ViewHolder(private var binding: ItemUserBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(detailResponse: DetailResponse) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(detailResponse.avatarUrl)
                    .placeholder(R.drawable.ic_baseline_photo_camera_24)
                    .error(R.drawable.ic_baseline_broken_image_24)
                    .into(civAvatar)
                tvProfileName.text = detailResponse.name
                tvUsername.text = detailResponse.login
                tvBio.text = detailResponse.bio

                if (detailResponse.name == "null") {
                    tvProfileName.visibility = View.GONE
                } else {
                    tvProfileName.visibility = View.VISIBLE
                }

                if (detailResponse.bio == "null") {
                    tvBio.visibility = View.GONE
                } else {
                    tvBio.visibility = View.VISIBLE
                }
                root.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_USER, detailResponse)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listUserResponse[position])
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUserResponse[position]) }
    }

    override fun getItemCount() = listUserResponse.size

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: DetailResponse)
    }
}