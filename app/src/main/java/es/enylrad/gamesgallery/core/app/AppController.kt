package es.enylrad.gamesgallery.core.app

import android.app.Application
import es.enylrad.gamesgallery.BuildConfig
import es.enylrad.gamesgallery.commons.di.firestoreModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class AppController : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initTimber()
    }

    private fun initKoin() {

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@AppController)
            modules(
                listOf(
                    firestoreModule
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