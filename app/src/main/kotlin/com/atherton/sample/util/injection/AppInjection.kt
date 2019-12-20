package com.atherton.sample.util.injection

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.net.ConnectivityManager
import com.atherton.sample.App
import com.atherton.sample.BuildConfig
import com.atherton.sample.data.db.RoomDb
import com.atherton.sample.data.db.dao.*
import com.atherton.sample.data.local.AppSettings
import com.atherton.sample.data.local.SharedPreferencesStorage
import com.atherton.sample.data.network.service.*
import com.atherton.sample.data.repository.*
import com.atherton.sample.domain.repository.*
import com.atherton.sample.presentation.util.AndroidAppStringProvider
import com.atherton.sample.presentation.util.AppStringProvider
import com.atherton.sample.presentation.util.image.ImageLoader
import com.atherton.sample.util.network.manager.AndroidNetworkManager
import com.atherton.sample.util.network.manager.NetworkManager
import com.atherton.sample.util.network.retrofit.KotlinRxJava2CallAdapterFactory
import com.atherton.sample.util.threading.RxSchedulers
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class, RepositoryModule::class, ServiceModule::class, DatabaseModule::class]
)
interface AppComponent {

    @ApplicationContext fun context(): Context
    fun schedulers(): RxSchedulers
    fun settings(): AppSettings
    fun appStringProvider(): AppStringProvider
    fun imageLoader(): ImageLoader
    fun settingsRepository(): SettingsRepository
    fun roomDb(): RoomDb
}


@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton @ApplicationContext
    internal fun provideApplicationContext(): Context = application

    @Provides
    @Singleton internal fun provideApplication(): App = application as App

    @Provides
    @Singleton internal fun provideSharedPrefs(): SharedPreferences =
        application.getSharedPreferences("com.atherton.sample_preferences", Context.MODE_PRIVATE)

    @Provides
    @Singleton internal fun provideNetworkManager(): NetworkManager {
        val manager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return AndroidNetworkManager(manager)
    }

    @Provides
    @Singleton internal fun provideSchedulers(): RxSchedulers = RxSchedulers(
        io = Schedulers.io(),
        main = AndroidSchedulers.mainThread()
    )

    @Provides
    @Singleton internal fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton internal fun provideRetrofit(moshi: Moshi): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(SAMPLE_API_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(KotlinRxJava2CallAdapterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton internal fun provideSettings(
        sharedPreferences: SharedPreferencesStorage
    ): AppSettings = sharedPreferences

    @Provides
    @Singleton internal fun provideAppStringProvider(
        androidAppStringProvider: AndroidAppStringProvider
    ): AppStringProvider = androidAppStringProvider

    @Provides
    @Singleton internal fun provideResources(): Resources = application.resources

    companion object {
        private const val SAMPLE_API_VERSION = 3
        private const val SAMPLE_API_HOST = "api.themoviedb.org"
        private const val SAMPLE_API_URL = "https://$SAMPLE_API_HOST/$SAMPLE_API_VERSION/"
    }
}

@Module
class RepositoryModule {

    @Provides
    @Singleton internal fun provideSampleRepository(
        sampleDao: SampleDao,
        sampleService: SampleService
    ): SampleRepository {
        return CachingSampleRepository(sampleDao, sampleService)
    }

    @Provides
    @Singleton internal fun provideSettingsRepository(appSettings: AppSettings): SettingsRepository {
        return CachingSettingsRepository(appSettings)
    }
}

@Module
class ServiceModule {

    @Provides
    @Singleton internal fun provideSampleService(retrofit: Retrofit): SampleService =
        retrofit.create(SampleService::class.java)
}

@Module
class DatabaseModule {

    @Provides
    @Singleton internal fun provideRoomDb(@ApplicationContext context: Context): RoomDb {
        return RoomDb.getInstance(context = context, useInMemory = false)
    }

    @Provides
    @Singleton internal fun provideSampleDao(roomDb: RoomDb): SampleDao = roomDb.getSampleDao()
}
