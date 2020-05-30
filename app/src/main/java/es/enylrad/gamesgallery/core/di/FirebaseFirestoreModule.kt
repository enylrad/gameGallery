package es.enylrad.gamesgallery.core.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import org.koin.dsl.module

val firestoreModule = module {
    single { provideFirestore() }
}

fun provideFirestore(): FirebaseFirestore {
    val firebase = FirebaseFirestore.getInstance()
    val settings = FirebaseFirestoreSettings.Builder()
        .build()
    firebase.firestoreSettings = settings
    return firebase
}