package com.huania.eew_bid

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        instance = applicationContext
    }

    companion object {
        lateinit var instance: Context
    }
}