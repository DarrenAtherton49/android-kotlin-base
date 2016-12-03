package com.example

import android.app.Application
import com.example.common.injection.component.AppComponent
import com.example.common.injection.component.DaggerAppComponent
import com.example.common.injection.module.AppModule

class ExampleApplication : Application() {

    internal lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initInjection()
    }

    private fun initInjection() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}