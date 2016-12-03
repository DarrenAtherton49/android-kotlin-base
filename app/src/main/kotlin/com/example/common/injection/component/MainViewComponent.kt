package com.example.common.injection.component

import com.example.common.injection.module.ActivityModule
import com.example.common.injection.module.MainViewModule
import com.example.common.injection.scope.PerScreen
import com.example.main.MainActivity
import dagger.Component

@PerScreen
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(ActivityModule::class, MainViewModule::class))
interface MainViewComponent : ActivityComponent {

    fun inject(mainActivity: MainActivity)
}