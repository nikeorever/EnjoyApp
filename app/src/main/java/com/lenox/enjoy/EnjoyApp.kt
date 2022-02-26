package com.lenox.enjoy

import android.app.Application
import com.lenox.enjoy.flutter.warmFlutterEngine

class EnjoyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        warmFlutterEngine(this)
    }
}