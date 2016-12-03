package com.example.base.presentation

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import com.example.ExampleApplication
import com.example.common.injection.component.AppComponent
import com.example.common.injection.module.ActivityModule

abstract class BaseActivity<View: BaseView, out Presenter : BasePresenter<View>> : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // The activity is being created.
        super.onCreate(savedInstanceState)

        setContentView(getLayoutResId())

        initInjection()

        getPresenter().onViewAttached(getPassiveView())
    }

    override fun onStart() {
        // The activity is about to become visible.
        super.onStart()
    }

    override fun onResume() {
        // The activity has become visible (it is now "resumed").
        super.onResume()
    }

    override fun onPause() {
        // Another activity is taking focus (this activity is about to be "paused").
        super.onPause()
    }

    override fun onStop() {
        // The activity is no longer visible (it is now "stopped")
        super.onStop()
    }

    override fun onDestroy() {
        // The activity is about to be destroyed.
        super.onDestroy()
        getPresenter().onViewDetached()
    }

    protected fun getAppComponent(): AppComponent {
        return (application as ExampleApplication).appComponent
    }

    protected fun getActivityModule(): ActivityModule {
        return ActivityModule(this)
    }

    @LayoutRes protected abstract fun getLayoutResId(): Int

    protected abstract fun getPassiveView(): View

    protected abstract fun getPresenter(): Presenter

    protected abstract fun initInjection()
}