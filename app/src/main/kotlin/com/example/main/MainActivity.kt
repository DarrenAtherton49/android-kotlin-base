package com.example.main

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.example.R
import com.example.base.presentation.BaseActivity
import com.example.common.injection.component.DaggerMainViewComponent
import com.example.common.injection.component.MainViewComponent
import javax.inject.Inject

class MainActivity : BaseActivity<MainPresenter.View, MainPresenter>(),
        MainPresenter.View {

    private lateinit var mainViewComponent: MainViewComponent
    override val passiveView: MainPresenter.View = this
    @Inject override lateinit var presenter: MainPresenter
    @LayoutRes override val layoutResId: Int = R.layout.activity_main

    //===================================================================================
    // Lifecycle methods and initialization
    //===================================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { presenter.onButtonClicked() }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    //===================================================================================
    // Dependency injection
    //===================================================================================

    override fun initInjection() {
        mainViewComponent = DaggerMainViewComponent.builder()
                .appComponent(appComponent())
                .activityModule(activityModule())
                .build()
        mainViewComponent.inject(this)
    }

    //===================================================================================
    // View methods
    //===================================================================================

    override fun showMessage() {
        Snackbar.make(findViewById(android.R.id.content),
                "Button clicked", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
    }
}
