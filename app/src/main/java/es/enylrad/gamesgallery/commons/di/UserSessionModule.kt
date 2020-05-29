package es.enylrad.gamesgallery.commons.di

import es.enylrad.gamesgallery.commons.model.UserEntity
import org.koin.dsl.module

val userSessionModule = module {
    single { provideUserSession() }
}

fun provideUserSession(): UserEntity {
    return UserEntity()
}
