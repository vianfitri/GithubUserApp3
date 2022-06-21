package com.vianfitri.githubuserapp3.ui.main

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import android.os.Bundle
import android.view.MenuItem
import com.vianfitri.githubuserapp3.R
import com.vianfitri.githubuserapp3.adapter.UserAdapter
import com.vianfitri.githubuserapp3.databinding.ActivityMainBinding
import com.vianfitri.githubuserapp3.datasource.DetailResponse

class MainActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener {

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

    override fun onMenuItemClick(p0: MenuItem?): Boolean {
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}