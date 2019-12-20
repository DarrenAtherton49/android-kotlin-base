package com.atherton.sample.presentation.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.navigation.NavController

class AndroidNavigator(private val navController: NavController, val context: Context) : Navigator {

    override fun showSettingsScreen() {
        navController.navigate(com.atherton.sample.R.id.actionSharedGoToSettings)
    }

    override fun showLicensesScreen() {
        navController.navigate(com.atherton.sample.R.id.actionSharedGoToLicenses)
    }

    override fun showUrlInBrowser(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(browserIntent)
    }
}
