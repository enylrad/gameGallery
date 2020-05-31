package es.enylrad.gamesgallery.commons.utils

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

fun ProgressBar.hide() {
    visibility = View.GONE
}

fun ProgressBar.show() {
    visibility = View.VISIBLE
}


fun View.snack(
    text: String,
    @BaseTransientBottomBar.Duration duration: Int = Snackbar.LENGTH_SHORT
) {
    Snackbar.make(this, text, duration).show()
}

fun View.snack(
    @StringRes text: Int,
    @BaseTransientBottomBar.Duration duration: Int = Snackbar.LENGTH_SHORT
) {
    Snackbar.make(this, resources.getString(text), duration).show()
}

fun Context.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}

fun Context.toast(@StringRes text: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, resources.getString(text), duration).show()
}