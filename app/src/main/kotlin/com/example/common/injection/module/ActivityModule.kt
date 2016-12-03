package com.example.common.injection.module

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import com.example.common.injection.scope.PerScreen
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    /**
     * Exposes the activity to dependents in the graph.
     */
    @Provides @PerScreen internal fun activity(): Activity {
        return activity
    }
}