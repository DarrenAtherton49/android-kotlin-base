package com.atherton.sample.presentation.features.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.atherton.sample.R
import com.atherton.sample.presentation.main.*
import com.atherton.sample.presentation.util.extension.getActivityViewModel
import com.atherton.sample.presentation.util.extension.getAppComponent
import com.atherton.sample.presentation.util.glide.GlideApp
import com.atherton.sample.presentation.util.image.ImageLoader
import kotlinx.android.synthetic.main.base_app_bar.*
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject
import javax.inject.Named

class SettingsFragment : Fragment() {

    @Inject
    @field:Named(MainViewModelFactory.NAME)
    lateinit var mainVmFactory: ViewModelProvider.Factory

    private val sharedViewModel: MainViewModel by lazy { getActivityViewModel<MainViewModel>(mainVmFactory) }

    private val mainActivity: MainActivity by lazy { activity as MainActivity }

    @Inject lateinit var imageLoader: ImageLoader

    private val recyclerViewAdapter: SettingsAdapter by lazy {
        SettingsAdapter(imageLoader, GlideApp.with(this)) { setting ->
            onSettingClicked(setting)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInjection()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        initRecyclerView()
    }

    private fun setupToolbar() {
        toolbar.setupWithNavController(mainActivity.navController, mainActivity.appBarConfiguration)
        toolbar.title = getString(R.string.fragment_label_settings)
    }

    private fun initRecyclerView() {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = recyclerViewAdapter
        recyclerViewAdapter.submitList(populateSettings())
    }

    private fun populateSettings(): List<Setting> {
        return listOf(
            Setting.OpenSourceLicenses(
                getString(R.string.settings_open_source_licenses),
                R.drawable.ic_description_white_24dp
            )
        )
    }

    private fun onSettingClicked(setting: Setting) {
        when (setting) {
            is Setting.OpenSourceLicenses -> sharedViewModel.dispatch(MainAction.SettingsAction.OpenSourceLicensesClicked)
        }
    }

    private fun initInjection() {
        DaggerSettingsComponent.builder()
            .mainModule(MainModule(null))
            .appComponent(getAppComponent())
            .build()
            .inject(this)
    }
}

// id is used for RecyclerView adapter stable id's
sealed class Setting(val id: Long, open val title: String, open val logoResId: Int) {
    data class OpenSourceLicenses(override val title: String, override val logoResId: Int) : Setting(1, title, logoResId)
}

