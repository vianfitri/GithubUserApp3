package com.vianfitri.githubuserapp3.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vianfitri.githubuserapp3.ui.main.MainActivity
import com.vianfitri.githubuserapp3.R
import kotlinx.coroutines.*

class Splash : AppCompatActivity() {
    val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Splash)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        activityScope.launch {
            delay(DELAY_TIME)
            val intent = Intent(this@Splash, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }

    companion object {
        const val DELAY_TIME = 2000L
    }
}