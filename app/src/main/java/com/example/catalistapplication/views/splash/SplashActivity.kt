package com.example.catalistapplication.views.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import com.example.catalistapplication.R
import com.example.catalistapplication.utils.SessionManager
import com.example.catalistapplication.views.home.HomeActivity
import com.example.catalistapplication.views.login.LoginActivity
import java.util.*
import kotlin.concurrent.timerTask

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setTransparentStatus(window)
        setUpDelay()
    }

    private fun setTransparentStatus(window: Window) {
        window.statusBarColor = Color.TRANSPARENT;
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    private fun setUpDelay() {
        Timer().schedule(timerTask {
            if (SessionManager.getIsLogin()) {
                startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            }
        }, 2000)

    }

}