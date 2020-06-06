package es.enylrad.gamesgallery.commons.utils

import com.google.firebase.crashlytics.FirebaseCrashlytics

fun reportCrash(exception: Exception?, tag: String) {
    exception?.let {
        send("$tag: ${exception.message}")
    }
}

fun reportCrash(throwable: Throwable?, tag: String) {
    throwable?.let {
        send("$tag: ${throwable.message}")
    }
}

private fun send(message: String) {
    // TODO Improvements in this reports
    FirebaseCrashlytics.getInstance().log(message)
}