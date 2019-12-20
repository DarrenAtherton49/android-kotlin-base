package com.atherton.sample.data.local

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesStorage @Inject constructor(private val sharedPreferences: SharedPreferences) : AppSettings {

}
