package com.atherton.sample.presentation.features.settings.licenses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.atherton.sample.R
import com.atherton.sample.presentation.main.*
import com.atherton.sample.presentation.util.extension.getActivityViewModel
import com.atherton.sample.presentation.util.extension.getAppComponent
import kotlinx.android.synthetic.main.base_app_bar.*
import kotlinx.android.synthetic.main.fragment_licenses.*
import javax.inject.Inject
import javax.inject.Named

class LicensesFragment : Fragment() {

    @Inject
    @field:Named(MainViewModelFactory.NAME)
    lateinit var mainVmFactory: ViewModelProvider.Factory

    private val sharedViewModel: MainViewModel by lazy { getActivityViewModel<MainViewModel>(mainVmFactory) }

    private val mainActivity: MainActivity by lazy { activity as MainActivity }

    private val recyclerViewAdapter: LicensesAdapter by lazy {
        LicensesAdapter { license ->
            onViewLicenseClicked(license)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInjection()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_licenses, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        initRecyclerView()
    }

    private fun setupToolbar() {
        toolbar.setupWithNavController(mainActivity.navController, mainActivity.appBarConfiguration)
        toolbar.title = getString(R.string.fragment_label_licenses)
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            adapter = recyclerViewAdapter
        }
        recyclerViewAdapter.submitList(generateLicenses(requireContext()))
    }

    private fun onViewLicenseClicked(license: License) {
        sharedViewModel.dispatch(MainAction.LicenseClicked(license))
    }

    private fun initInjection() {
        DaggerLicensesComponent.builder()
            .mainModule(MainModule(null))
            .appComponent(getAppComponent())
            .build()
            .inject(this)
    }
}

data class License(
    val id: Long, // used for RecyclerView stable id's
    val name: String,
    val contributor: String,
    val description: String,
    val url: String
)
