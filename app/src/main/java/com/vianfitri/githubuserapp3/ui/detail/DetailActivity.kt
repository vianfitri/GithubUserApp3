package com.vianfitri.githubuserapp3.ui.detail

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.vianfitri.githubuserapp3.R
import com.vianfitri.githubuserapp3.adapter.SectionsPagerAdapter
import com.vianfitri.githubuserapp3.database.FavoriteEntity
import com.vianfitri.githubuserapp3.databinding.ActivityDetailBinding
import com.vianfitri.githubuserapp3.datasource.DetailResponse
import com.vianfitri.githubuserapp3.repository.UserRepository
import kotlin.math.ln
import kotlin.math.pow

class DetailActivity : AppCompatActivity() {
    private val android.content.Context.dataStore:
            DataStore<Preferences> by preferencesDataStore(name = "settings")

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    private var isFavorite = false

    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userd = intent.getParcelableExtra<DetailResponse>(EXTRA_USER) as DetailResponse

        val favorite = FavoriteEntity()

        setViewModel(favorite)
        showDetailUserData(userd)
        listenFavorite(favorite)
        setupTabFollow()

    }

    private fun showDetailUserData(user: DetailResponse?) {

        binding.apply {
            Glide.with(this@DetailActivity)
                .load(user?.avatarUrl)
                .placeholder(R.drawable.ic_baseline_photo_camera_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(ivProfilePic)
            tvDetailName.text = user?.name
            tvDetailUsername.text = user?.login
            tvDetailCompany.text = user?.company
            tvDetailLocation.text = user?.location
            tvDetailFollowerCount.text = user?.followers?.let { getFormatedNumber(it.toLong()) }
            tvDetailFollowingCount.text =
                """● ${user?.following?.let { getFormatedNumber(it.toLong()) }}"""
            tvDetailRepositoryCount.text = user?.publicRepos?.let { getFormatedNumber(it.toLong()) }

            if (user?.name == "null") { tvDetailName.visibility = View.GONE }
            else { tvDetailName.visibility = View.VISIBLE }

            if (user?.company == "null") {
                tvDetailCompany.visibility = View.GONE
                ivIconCompany.visibility = View.GONE
            } else {
                tvDetailCompany.visibility = View.VISIBLE
                ivIconCompany.visibility = View.VISIBLE
            }

            if (user?.location == "null") {
                tvDetailLocation.visibility = View.GONE
                ivIconLocation.visibility = View.GONE
            } else {
                tvDetailLocation.visibility = View.VISIBLE
                ivIconLocation.visibility = View.VISIBLE
            }
        }
    }

    private fun getFormatedNumber(count: Long) : String {
        if (count < 1000) return "" + count
        val exp = (ln(count.toDouble()) / ln(1000.0)).toInt()
        return String.format("%.1f %c", count / 1000.0.pow(exp.toDouble()), "kMGTPE"[exp - 1])
    }

    private fun setupTabFollow(){
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun listenFavorite(favorite: FavoriteEntity){
        detailViewModel.getFavoriteByLogin(favorite.login!!)
            .observe(this@DetailActivity) { listFav ->
                isFavorite = listFav.isNotEmpty()

                binding.detailFabFavorite.imageTintList = if (listFav.isEmpty()) {
                    ColorStateList.valueOf(Color.rgb(255, 255, 255))
                } else {
                    ColorStateList.valueOf(Color.rgb(247, 106, 123))
                }

            }

        binding.detailFabFavorite.apply {
            setOnClickListener {
                if (isFavorite) {
                    detailViewModel.delete(favorite)
                    makeText(
                        this@DetailActivity,
                        "${favorite.login} telah dihapus dari data User Favorite ",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    detailViewModel.insert(favorite)
                    makeText(
                        this@DetailActivity,
                        "${favorite.login} telah ditambahkan ke data User Favorite",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun setViewModel(fav: FavoriteEntity) {
        detailViewModel =
            ViewModelProvider(this, DetailViewModelFactory(fav.login!!, application))[DetailViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}