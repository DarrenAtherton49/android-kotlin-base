package com.atherton.sample.presentation.util.extension

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.viewpager.widget.ViewPager
import com.atherton.sample.App
import com.atherton.sample.util.injection.AppComponent
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun <T> Observable<T>.preventMultipleClicks(): Observable<T> {
    return this.throttleFirst(300, TimeUnit.MILLISECONDS)
}

inline fun View.setOnGesturesListener(crossinline onDoubleTap: () -> Unit, crossinline onLongPress: () -> Unit) {
    val listener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent): Boolean {
            onDoubleTap.invoke()
            return true
        }

        override fun onLongPress(e: MotionEvent) {
            onLongPress.invoke()
        }
    }
    val detector = GestureDetector(context, listener).apply {
        setOnDoubleTapListener(listener)
        setIsLongpressEnabled(true)
    }
    this.setOnTouchListener { _, event -> detector.onTouchEvent(event) }
}

inline fun ViewPager.onPageChanged(crossinline block: (page: Int) -> Unit) {
    this.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {}

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

        override fun onPageSelected(position: Int) {
            block.invoke(position)
        }
    })
}

fun Context.getColorCompat(@ColorRes colorResId: Int) = ContextCompat.getColor(this, colorResId)

fun Context.getDrawableCompat(@DrawableRes id: Int): Drawable? = ContextCompat.getDrawable(this, id)

fun ViewGroup.inflateLayout(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

internal fun Context.getAppComponent(): AppComponent {
    return if (this is Application) {
        (this as App).appComponent
    } else {
        (this.applicationContext as App).appComponent
    }
}

internal fun Fragment.getAppComponent(): AppComponent = (this.requireContext().getAppComponent())

internal fun View.getAppComponent(): AppComponent = (this.context.getAppComponent())

fun <T> LiveData<T>.observeLiveData(owner: LifecycleOwner, observer: (T?) -> Unit) {
    this.observe(owner, Observer(observer))
}

inline fun <reified T : ViewModel> Fragment.getViewModel(vmFactory: ViewModelProvider.Factory): T {
    return ViewModelProviders.of(this, vmFactory).get(T::class.java)
}

inline fun <reified T : ViewModel> Fragment.getActivityViewModel(vmFactory: ViewModelProvider.Factory): T {
    return ViewModelProviders.of(requireActivity(), vmFactory).get(T::class.java)
}

inline fun <reified T : ViewModel> AppCompatActivity.getViewModel(vmFactory: ViewModelProvider.Factory): T {
    return ViewModelProviders.of(this, vmFactory).get(T::class.java)
}

fun View.showShortSnackbar(
    text: String,
    actionText: String? = null,
    onClickListener: (() -> Unit)? = null
) {
    val snackbar = Snackbar.make(this, text, Snackbar.LENGTH_SHORT)
    if (actionText != null && onClickListener != null) {
        snackbar.setAction(actionText) { onClickListener.invoke() }
    }
    snackbar.show()
}

fun View.showLongSnackbar(
    text: String,
    actionText: String? = null,
    onClick: (() -> Unit)? = null
) {
    val snackbar = Snackbar.make(this, text, Snackbar.LENGTH_LONG)
    if (actionText != null && onClick != null) {
        snackbar.setAction(actionText) { onClick.invoke() }
    }
    snackbar.show()
}

inline var View.isVisible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

fun FloatingActionButton.show(show: Boolean) {
    if (show) show() else hide()
}

fun View.showSoftKeyboard() {
    if (requestFocus()) {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun View.hideSoftKeyboard() {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

fun EditText.whenTextChanges(emitInitialValue: Boolean = false, block: (String) -> Unit) {
    if (emitInitialValue) {
        block.invoke(this.text.toString())
    }
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let { block.invoke(it.toString()) }
        }
    })
}

fun TextView.setTextOrHide(text: String?) {
    if (text != null) {
        isVisible = true
        setText(text)
    } else {
        isVisible = false
    }
}
