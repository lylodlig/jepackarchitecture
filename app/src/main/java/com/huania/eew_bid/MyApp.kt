package com.huania.eew_bid

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.baidu.mapapi.SDKInitializer

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        instance = applicationContext
        SDKInitializer.initialize(this)
    }

    companion object {
        lateinit var instance: Context
    }
}