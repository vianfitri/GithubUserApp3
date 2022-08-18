package com.vianfitri.githubuserapp3.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vianfitri.githubuserapp3.adapter.FavoriteAdapter
import com.vianfitri.githubuserapp3.database.FavoriteEntity
import com.vianfitri.githubuserapp3.databinding.ActivityFavoriteBinding
import com.vianfitri.githubuserapp3.ui.detail.DetailActivity


class FavoriteActivity : AppCompatActivity() {
    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding!!
    private val adapter: FavoriteAdapter by lazy {
        FavoriteAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpList()
        setUserFavorite()
    }

    private fun setUserFavorite(){
        val favoriteViewModel = obtainViewModel(this@FavoriteActivity)
        favoriteViewModel.getAllFavorite().observe(this) { favList ->
            if (favList != null) {
                adapter.setListFavorite(favList)
                binding.favNoData.visibility = View.GONE
            }
            if (favList.isEmpty()) {
                binding.favNoData.visibility = View.VISIBLE
            } else {
                binding.favNoData.visibility = View.VISIBLE
            }
        }
    }

    private fun setUpList() {
        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.setHasFixedSize(true)
        binding.rvFavorite.adapter = adapter
        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(favEntity: FavoriteEntity) {
                val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USER, favEntity)
                startActivity(intent)
            }
        })
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}