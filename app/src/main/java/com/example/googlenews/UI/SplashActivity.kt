package com.example.googlenews.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.googlenews.R

class SplashActivity : AppCompatActivity() {
    val SPLASH_TIME_OUT:Int =1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            val Intent = Intent(this, LoginActivity::class.java)
            startActivity(Intent)
            finish()
        },SPLASH_TIME_OUT.toLong())
    }
}