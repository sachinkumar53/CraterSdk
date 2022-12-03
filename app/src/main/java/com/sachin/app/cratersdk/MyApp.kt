package com.sachin.app.cratersdk

import android.app.Application
import com.crater.android.Crater
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application(){

    override fun onCreate() {
        super.onCreate()
        Crater.init()
    }
}