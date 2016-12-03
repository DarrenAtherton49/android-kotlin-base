package com.example.base.presentation

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.ExampleApplication
import com.example.common.injection.component.AppComponent
import com.example.common.injection.module.ActivityModule

abstract class BaseFragment<View: BaseView, out Presenter : BasePresenter<View>> : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initInjection()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): android.view.View {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(getLayoutResId(), container, false)
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPresenter().onViewAttached(getPassiveView())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        getPresenter().onViewDetached()
    }

    protected fun getAppComponent(): AppComponent {
        return (getAct().application as ExampleApplication).appComponent
    }

    protected fun getActivityModule(): ActivityModule {
        return ActivityModule(getAct())
    }

    private fun getAct(): AppCompatActivity {
        return activity as AppCompatActivity
    }

    @LayoutRes protected abstract fun getLayoutResId(): Int

    protected abstract fun getPassiveView(): View

    protected abstract fun getPresenter(): Presenter

    protected abstract fun initInjection()
}