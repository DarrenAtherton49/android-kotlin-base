package com.example.common.injection.component

import android.app.Activity
import com.example.common.injection.module.ActivityModule
import com.example.common.injection.scope.PerScreen
import dagger.Component

@PerScreen
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun activity(): Activity
}
