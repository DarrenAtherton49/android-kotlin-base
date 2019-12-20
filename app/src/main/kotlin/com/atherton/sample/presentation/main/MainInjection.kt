package com.atherton.sample.presentation.main

import androidx.lifecycle.ViewModelProvider
import com.atherton.sample.util.injection.AppComponent
import com.atherton.sample.util.injection.PerView
import com.atherton.sample.util.threading.RxSchedulers
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Named

@PerView
@Component(
    dependencies = [AppComponent::class],
    modules = [MainModule::class]
)
interface MainComponent {

    fun inject(mainActivity: MainActivity)
}


@Module
class MainModule(private val initialState: MainState?) {

    @Provides
    @Named(MainViewModelFactory.NAME)
    @PerView internal fun provideViewModelFactory(schedulers: RxSchedulers): ViewModelProvider.Factory {
        return MainViewModelFactory(
            initialState,
            schedulers
        )
    }
}
