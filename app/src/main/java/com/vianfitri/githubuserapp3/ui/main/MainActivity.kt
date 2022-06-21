package com.vianfitri.githubuserapp3.ui.main

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import android.os.Bundle
import android.view.MenuItem
import com.vianfitri.githubuserapp3.R
import com.vianfitri.githubuserapp3.adapter.UserAdapter
import com.vianfitri.githubuserapp3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener {

    private lateinit var adapter: UserAdapter
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Github User's Search"

    }

    private fun recyclerViewConfig(){
        binding.rvUser.layoutManager = LinearLayoutManager(binding.rvUser.context)
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.adapter = adapter
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