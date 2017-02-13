package com.example.common.injection.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.darrenatherton.droidcommunity.common.threading.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger module that provides objects which last for the entire app lifecycle
 */
@Module
class AppModule(private val application: Application) {

    @Provides @Singleton internal fun provideApplicationContext(): Context {
        return application
    }

    @Provides @Singleton internal fun provideSharedPrefs(): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

    @Provides @Singleton internal fun provideUiThread(androidUiThread: AndroidUiThread): UiThread {
        return androidUiThread
    }

    @Provides @Singleton internal fun provideIoExecutor(
            rxIoExecutor: RxIoExecutor): IoExecutor {
        return rxIoExecutor
    }

    @Provides @Singleton internal fun provideComputationExecutor(
            rxComputationExecutor: RxComputationExecutor): ComputationExecutor {
        return rxComputationExecutor
    }
}
