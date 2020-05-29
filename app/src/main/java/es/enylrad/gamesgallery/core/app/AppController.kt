package es.enylrad.gamesgallery.core.app

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import es.enylrad.gamesgallery.BuildConfig
import es.enylrad.gamesgallery.commons.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class AppController : Application() {

    override fun onCreate() {
        super.onCreate()
        initCrashlytics()
        initKoin()
        initTimber()
    }

    private fun initCrashlytics() {
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)
    }

    private fun initKoin() {

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@AppController)
            modules(
                listOf(
                    userSessionModule,
                    preferencesModule,
                    firestoreModule,
                    retrofitModule,
                    viewModelModule
                )
            )
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}