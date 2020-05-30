package es.enylrad.gamesgallery.core.di

import android.app.Application
import es.enylrad.gamesgallery.core.db.AppDatabase
import org.koin.dsl.module

val roomModule = module {
    single { provideDb(get()) }
    single { provideGamesDao(get()) }
}

fun provideDb(app: Application) = AppDatabase.getInstance(app)

fun provideGamesDao(db: AppDatabase) = db.gamesDAO()