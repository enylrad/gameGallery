package es.enylrad.gamesgallery.commons.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import es.enylrad.gamesgallery.ui.library.LibraryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val firestoreModule = module {

    single { provideFirestore() }

    viewModel {
        LibraryViewModel(get())
    }
}

fun provideFirestore(): FirebaseFirestore {
    val firebase = FirebaseFirestore.getInstance()
    val settings = FirebaseFirestoreSettings.Builder()
        .build()
    firebase.firestoreSettings = settings
    return firebase
}