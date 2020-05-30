package es.enylrad.gamesgallery.core.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import es.enylrad.gamesgallery.BuildConfig
import org.koin.dsl.module

val preferencesModule = module {
    single { provideSettingsPreferences(get()) }
}

const val PREFERENCES_FILE_KEY = "${BuildConfig.APPLICATION_ID}.share_preferences"

private fun provideSettingsPreferences(app: Application): SharedPreferences =
    app.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
