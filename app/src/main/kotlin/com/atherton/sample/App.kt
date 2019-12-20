package com.atherton.sample

import android.app.Application
import android.os.Looper
import com.atherton.sample.util.extension.ioThread
import com.atherton.sample.util.extension.onAndroidPieOrLater
import com.atherton.sample.util.injection.AppComponent
import com.atherton.sample.util.injection.AppModule
import com.atherton.sample.util.injection.DaggerAppComponent
import com.squareup.leakcanary.LeakCanary
import com.ww.roxie.Roxie
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber

internal class App : Application() {

    internal lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }

        /*
         * Only using LeakCanary on devices running lower than Android P due to a bug in AOSP causing
         * excessive leaks. See https://github.com/square/leakcanary/issues/1081.
         */
        if (!onAndroidPieOrLater()) {
            LeakCanary.install(this)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        Roxie.enableLogging(object : Roxie.Logger {
            override fun log(msg: String) {
                Timber.tag("Roxie").d(msg)
            }
        })

        // https://www.zacsweers.dev/rxandroids-new-async-api/
        RxAndroidPlugins.setInitMainThreadSchedulerHandler {
            AndroidSchedulers.from(Looper.getMainLooper(), true)
        }

        initInjection()

        startDatabase()
    }

    private fun initInjection() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    private fun startDatabase() {
        ioThread {
            // we call a function on one of the room Dao's so that the database gets created and the pre-populated
            // data is inserted. Note that it doesn't matter which Dao function gets called, we just need to create
            // the database before navigating to the initial screen so that the initial data is there.
            //appComponent.roomDb().getSampleDao().getConfig() //todo
        }
    }
}
