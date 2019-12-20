package com.atherton.sample.presentation.base

import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.atherton.sample.presentation.util.extension.observeLiveData
import com.ww.roxie.BaseAction
import com.ww.roxie.BaseState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

abstract class BaseActivity<Action : BaseAction,
    State,
    ViewEffect : BaseViewEffect,
    ViewModel : AppViewModel<Action, State, ViewEffect>>
    : AppCompatActivity()
    where State : BaseState,
          State : Parcelable {

    protected abstract val layoutResId: Int
    protected abstract val stateBundleKey: String
    protected abstract val sharedViewModel: ViewModel

    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        // support process death by re-supplying last state to ViewModel
        val lastState: State? = savedInstanceState?.getParcelable(stateBundleKey)
        initInjection(lastState)

        super.onCreate(savedInstanceState)

        setContentView(layoutResId)

        sharedViewModel.observableState.observeLiveData(this) { state ->
            state?.let { renderState(state) }
        }
    }


    override fun onResume() {
        super.onResume()

        disposables += sharedViewModel.viewEffects()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                processViewEffects(it)
            }
    }

    override fun onPause() {
        super.onPause()
        disposables.clear()
    }

    // support process death by saving last ViewModel state in bundle
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(stateBundleKey, sharedViewModel.observableState.value)
    }

    protected abstract fun initInjection(initialState: State?)

    protected abstract fun renderState(state: State)

    protected abstract fun processViewEffects(viewEffect: ViewEffect)
}
