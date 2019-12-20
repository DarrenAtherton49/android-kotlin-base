package com.atherton.sample.presentation.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.atherton.sample.R
import com.atherton.sample.presentation.base.BaseActivity
import com.atherton.sample.presentation.navigation.AndroidNavigator
import com.atherton.sample.presentation.navigation.Navigator
import com.atherton.sample.presentation.util.extension.getAppComponent
import com.atherton.sample.presentation.util.extension.getViewModel
import com.atherton.sample.presentation.util.extension.isVisible
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import javax.inject.Named

class MainActivity : BaseActivity<MainAction, MainState, MainViewEffect, MainViewModel>() {

    override val layoutResId: Int = R.layout.activity_main
    override val stateBundleKey: String = "bundle_key_main_state"

    @Inject @field:Named(MainViewModelFactory.NAME)
    lateinit var vmFactory: ViewModelProvider.Factory

    override val sharedViewModel: MainViewModel by lazy { getViewModel<MainViewModel>(vmFactory) }
    val navController: NavController by lazy { findNavController(R.id.navHostFragment) }
    internal val navigator: Navigator by lazy { AndroidNavigator(navController, this) }

    private val topLevelDestinationIds = setOf(R.id.fragmentOne, R.id.fragmentTwo, R.id.fragmentThree)

    val appBarConfiguration: AppBarConfiguration by lazy {
        AppBarConfiguration(topLevelDestinationIds)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupNavigation()
        addBottomBarVisibilityListener()
    }

    override fun renderState(state: MainState) {}

    override fun processViewEffects(viewEffect: MainViewEffect) {
        when (viewEffect) {
            is MainViewEffect.Navigation.ShowSettingsScreen -> navigator.showSettingsScreen()
            is MainViewEffect.Navigation.Settings.ShowLicensesScreen -> navigator.showLicensesScreen()
            is MainViewEffect.Navigation.ShowLicenseInBrowser -> navigator.showUrlInBrowser(viewEffect.url)
        }
    }

    private fun setupNavigation() {
        bottomNavigation.setupWithNavController(navController)

        // use the below line if we don't want fragment to be re-created when user re-selects the same button on bottom nav
        //bottomNavigation.setOnNavigationItemReselectedListener {  }
    }

    private fun addBottomBarVisibilityListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNavigation.isVisible = destination.isTopLevel()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun initInjection(initialState: MainState?) {
        DaggerMainComponent.builder()
            .mainModule(MainModule(initialState))
            .appComponent(getAppComponent())
            .build()
            .inject(this)
    }

    private fun NavDestination.isTopLevel() = topLevelDestinationIds.contains(this.id)
}
