package com.example.common.injection.component

import android.content.Context
import android.content.SharedPreferences
import com.example.common.injection.module.AppModule
import dagger.Component
import javax.inject.Singleton

/**
 * Dagger component which lasts for the entire app lifecycle
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun context(): Context
    fun sharedPreferences(): SharedPreferences
}