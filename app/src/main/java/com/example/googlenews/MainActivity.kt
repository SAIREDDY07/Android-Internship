package com.example.googlenews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {
    val SPLASH_TIME_OUT:Int =4000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({
            val Intent = Intent(this,LoginActivity::class.java)
            startActivity(Intent)
            finish()
        },SPLASH_TIME_OUT.toLong())
    }
}