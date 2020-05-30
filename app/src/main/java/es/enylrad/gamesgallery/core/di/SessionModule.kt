package es.enylrad.gamesgallery.core.di

import es.enylrad.gamesgallery.core.model.UserEntity
import org.koin.dsl.module

val sessionModule = module {
    single { provideUserSession() }
}

fun provideUserSession(): UserEntity {
    return UserEntity()
}
