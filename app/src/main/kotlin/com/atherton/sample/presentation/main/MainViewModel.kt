package com.atherton.sample.presentation.main

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.atherton.sample.presentation.base.AppViewModel
import com.atherton.sample.presentation.base.BaseViewEffect
import com.atherton.sample.presentation.features.settings.licenses.License
import com.atherton.sample.presentation.util.extension.preventMultipleClicks
import com.atherton.sample.util.injection.PerView
import com.atherton.sample.util.threading.RxSchedulers
import com.ww.roxie.BaseAction
import com.ww.roxie.BaseState
import io.reactivex.Observable.mergeArray
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.parcel.Parcelize
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
    initialState: MainState?,
    private val schedulers: RxSchedulers
) : AppViewModel<MainAction, MainState, MainViewEffect>() {

    override val initialState = initialState ?: MainState()

    init {
        bindActions()
    }

    private fun bindActions() {

        val settingsActionClickedViewEffect = actions.ofType<MainAction.SettingsActionClicked>()
            .subscribeOn(schedulers.io)
            .map { MainViewEffect.Navigation.ShowSettingsScreen }

        val openSourceLicensesClickedViewEffect = actions.ofType<MainAction.SettingsAction.OpenSourceLicensesClicked>()
            .subscribeOn(schedulers.io)
            .preventMultipleClicks()
            .map { MainViewEffect.Navigation.Settings.ShowLicensesScreen }

        val licenseClickedViewEffect = actions.ofType<MainAction.LicenseClicked>()
            .subscribeOn(schedulers.io)
            .preventMultipleClicks()
            .map { action -> MainViewEffect.Navigation.ShowLicenseInBrowser(action.license.url) }

        val viewEffectChanges = mergeArray(
            settingsActionClickedViewEffect,
            openSourceLicensesClickedViewEffect,
            licenseClickedViewEffect
        )

        disposables += viewEffectChanges
            .observeOn(schedulers.main)
            .subscribe(viewEffects::accept, Timber::e)
    }
}

//================================================================================
// MVI
//================================================================================

sealed class MainAction : BaseAction {
    object SettingsActionClicked : MainAction()
    sealed class SettingsAction : MainAction() {
        object OpenSourceLicensesClicked : SettingsAction()
    }
    data class LicenseClicked(val license: License) : MainAction()
}

@Parcelize
data class MainState(val isIdle: Boolean = true): BaseState, Parcelable

sealed class MainViewEffect : BaseViewEffect {
    sealed class Navigation : MainViewEffect() {
        object ShowSettingsScreen : Navigation()
        sealed class Settings : Navigation() {
            object ShowLicensesScreen : Settings()
        }
        data class ShowLicenseInBrowser(val url: String) : Navigation()
    }
}

//================================================================================
// Factory
//================================================================================

@PerView
class MainViewModelFactory(
    private val initialState: MainState?,
    private val schedulers: RxSchedulers
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = MainViewModel(
        initialState,
        schedulers) as T

    companion object {
        const val NAME = "MainViewModelFactory"
    }
}
