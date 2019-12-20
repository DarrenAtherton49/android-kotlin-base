package com.atherton.sample.presentation.base

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.DialogFragment
import com.atherton.sample.presentation.main.MainModule
import com.atherton.sample.presentation.main.MainViewEffect
import com.atherton.sample.presentation.main.MainViewModel
import com.atherton.sample.presentation.util.extension.observeLiveData
import com.ww.roxie.BaseAction
import com.ww.roxie.BaseState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

abstract class BaseDialogFragment<Action : BaseAction,
    State,
    ViewEffect : BaseViewEffect,
    ViewModel : AppViewModel<Action, State, ViewEffect>>
    : DialogFragment()
    where State : BaseState,
          State : Parcelable {

    protected abstract val layoutResId: Int
    protected abstract val stateBundleKey: String
    protected abstract val viewModel: ViewModel
    protected abstract val sharedViewModel: MainViewModel

    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        // support process death by re-supplying last state to ViewModel
        val lastState: State? = savedInstanceState?.getParcelable(stateBundleKey)
        initInjection(lastState)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(layoutResId, container, false)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observableState.observeLiveData(viewLifecycleOwner) { state ->
            state?.let { renderState(state) }
        }
    }

    override fun onResume() {
        super.onResume()

        disposables += viewModel.viewEffects()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                processViewEffects(it)
            }

        disposables += sharedViewModel.viewEffects()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                processSharedViewEffects(it)
            }
    }

    override fun onPause() {
        super.onPause()
        disposables.clear()
    }

    // support process death by saving last ViewModel state in bundle
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(stateBundleKey, viewModel.observableState.value)
    }

    protected abstract fun initInjection(initialState: State?)

    protected abstract fun renderState(state: State)

    protected abstract fun processSharedViewEffects(viewEffect: MainViewEffect)

    protected abstract fun processViewEffects(viewEffect: ViewEffect)

    protected val mainModule: MainModule by lazy { MainModule(null) }
}
