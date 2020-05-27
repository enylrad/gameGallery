package es.enylrad.gamesgallery.ui.dashboard

import android.view.View
import androidx.databinding.BindingAdapter
import es.enylrad.gamesgallery.ui.dashboard.adapter.sealed.GameTypeAdapter

@BindingAdapter("hideList")
fun View.hideList(type: Int) {
    val hide = when (type) {
        GameTypeAdapter.SimpleGameAdapter().type -> {
            true
        }
        else -> {
            false
        }
    }

    visibility = if (hide) View.GONE else View.VISIBLE
}

@BindingAdapter("hideGrid")
fun View.hideGrid(type: Int) {
    val hide = when (type) {
        GameTypeAdapter.GridGameAdapter().type -> {
            true
        }
        else -> {
            false
        }
    }

    visibility = if (hide) View.GONE else View.VISIBLE
}

@BindingAdapter("hideCard")
fun View.hideCard(type: Int) {
    val hide = when (type) {
        GameTypeAdapter.CardGameAdapter().type -> {
            true
        }
        else -> {
            false
        }
    }

    visibility = if (hide) View.GONE else View.VISIBLE
}