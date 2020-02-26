package com.azhara.submission4dicoding.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.azhara.submission4dicoding.MainActivity
import com.azhara.submission4dicoding.R
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            animation_splash.speed = 0.0001F
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 4400)
    }
}
