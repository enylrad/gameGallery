package es.enylrad.gamesgallery.core.di

import es.enylrad.gamesgallery.core.db.data.GamesRemoteDataSource
import es.enylrad.gamesgallery.core.db.data.GamesRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { GamesRemoteDataSource(get()) }
    single { GamesRepository.getInstance(get(), get()) }
}