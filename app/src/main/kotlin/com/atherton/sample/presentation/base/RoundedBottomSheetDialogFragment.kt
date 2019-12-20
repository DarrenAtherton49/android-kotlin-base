package com.atherton.sample.presentation.base

import android.app.Dialog
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import com.atherton.sample.R
import com.atherton.sample.presentation.main.MainActivity
import com.atherton.sample.presentation.main.MainModule
import com.atherton.sample.presentation.navigation.Navigator
import com.atherton.sample.presentation.util.extension.observeLiveData
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ww.roxie.BaseAction
import com.ww.roxie.BaseState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

abstract class RoundedBottomSheetDialogFragment<Action : BaseAction,
    State,
    ViewEffect : BaseViewEffect,
    ViewModel : AppViewModel<Action, State, ViewEffect>>
    : BottomSheetDialogFragment()
    where State : BaseState,
          State : Parcelable {

    protected abstract val layoutResId: Int
    protected abstract val stateBundleKey: String
    protected abstract val viewModel: ViewModel

    protected val navigator: Navigator by lazy { mainActivity.navigator }

    private val mainActivity: MainActivity by lazy { activity as MainActivity }

    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun getTheme() = R.style.BottomSheetDialogTheme

    override fun onCreate(savedInstanceState: Bundle?) {
        // support process death by re-supplying last state to ViewModel
        val lastState: State? = savedInstanceState?.getParcelable(stateBundleKey)
        initInjection(lastState)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme)
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

    protected abstract fun processViewEffects(viewEffect: ViewEffect)

    protected val mainModule: MainModule by lazy { MainModule(null) }
}
