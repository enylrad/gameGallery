package es.enylrad.gamesgallery.core.di

import com.google.android.datatransport.runtime.dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val appModule = module {
    single { provideCoroutineScopeIO() }
}

@Provides
fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)