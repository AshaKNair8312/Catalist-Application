package com.example.catalistapplication.app

import android.app.Application
import com.example.catalistapplication.utils.SessionManager
import timber.log.Timber

class Base : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        SessionManager.initializeval(applicationContext)
    }
}