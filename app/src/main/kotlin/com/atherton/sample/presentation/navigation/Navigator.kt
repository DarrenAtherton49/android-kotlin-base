package com.atherton.sample.presentation.navigation

interface Navigator {
    fun showSettingsScreen()
    fun showLicensesScreen()
    fun showUrlInBrowser(url: String)
}
