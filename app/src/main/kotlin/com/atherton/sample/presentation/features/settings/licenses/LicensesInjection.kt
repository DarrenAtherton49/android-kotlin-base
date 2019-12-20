package com.atherton.sample.presentation.features.settings.licenses

import com.atherton.sample.presentation.main.MainComponent
import com.atherton.sample.presentation.main.MainModule
import com.atherton.sample.util.injection.AppComponent
import com.atherton.sample.util.injection.PerView
import dagger.Component
import dagger.Module

@PerView
@Component(
    dependencies = [AppComponent::class],
    modules = [MainModule::class, LicensesModule::class]
)
interface LicensesComponent : MainComponent {

    fun inject(licensesFragment: LicensesFragment)
}


@Module
class LicensesModule
