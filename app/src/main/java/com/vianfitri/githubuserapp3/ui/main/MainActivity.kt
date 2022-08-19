package com.vianfitri.githubuserapp3.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.vianfitri.githubuserapp3.R
import com.vianfitri.githubuserapp3.adapter.UserAdapter
import com.vianfitri.githubuserapp3.databinding.ActivityMainBinding
import com.vianfitri.githubuserapp3.datasource.DetailResponse
import com.vianfitri.githubuserapp3.repository.UserRepository
import com.vianfitri.githubuserapp3.ui.favorite.FavoriteActivity
import com.vianfitri.githubuserapp3.ui.setting.SettingActivity
import com.vianfitri.githubuserapp3.ui.setting.SettingPreferences

class MainActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener {
    private val android.content.Context.dataStore:
            DataStore<Preferences> by preferencesDataStore(name = "settings")

    private lateinit var adapter: UserAdapter
    private var listData = ArrayList<DetailResponse>()
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Github User's Search"

        adapter = UserAdapter(listData)

        setUpToolbar()
        recyclerViewConfig()
        setMainViewModel()
        observeProgressBar()
        themeCheck()
        searchUser()
        getUser()
    }

    private fun searchUser() {
        with(binding) {
            search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    if (query.isEmpty()) {
                        return true
                    } else {
                        UserRepository.searchUser(query)
                    }
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    if(query.isNullOrEmpty()) {
                        UserRepository.clearSearch()
                    }
                    return false
                }
            })
        }
    }

    private fun themeCheck() {
        mainViewModel.getThemeSettings().observe(this@MainActivity) {
                isDarkModeActive ->
            if (isDarkModeActive) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun setMainViewModel() {
        val pref = SettingPreferences.getInstance(dataStore)
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(pref))[MainViewModel::class.java]
    }

    private fun getUser() {
        mainViewModel.usersDetail.observe(this) { detailResponse ->
            if (detailResponse != null) {
                setUserData(detailResponse)
            }
        }
    }

    private fun setUserData(detailResponse: ArrayList<DetailResponse>) {
        with(binding) {
            rvUser.layoutManager = LinearLayoutManager(rvUser.context)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = UserAdapter(detailResponse)
        }
    }

    private fun observeProgressBar() {
        mainViewModel.isLoading.observe(this) {
            showProgressBar(it)
        }
    }

    private fun showProgressBar(isLoading: Boolean?) {
        binding.progressBar.visibility = if(isLoading == true) View.VISIBLE else View.GONE
    }

    private fun recyclerViewConfig(){
        with(binding) {
            rvUser.layoutManager = LinearLayoutManager(rvUser.context)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter
        }
    }

    private fun setUpToolbar() {
        binding.toolbar.setOnMenuItemClickListener(this)
    }

    override fun onMenuItemClick(mi: MenuItem?): Boolean {
        return when (mi?.itemId) {
            R.id.btn_setting -> {
                val setting = Intent(this, SettingActivity::class.java)
                startActivity(setting)
                true
            }
            R.id.btn_favorite -> {
                val favorite = Intent(this, FavoriteActivity::class.java)
                startActivity(favorite)
                true
            }
            else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}