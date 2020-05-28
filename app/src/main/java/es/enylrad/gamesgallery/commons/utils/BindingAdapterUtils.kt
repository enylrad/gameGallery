package es.enylrad.gamesgallery.commons.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import es.enylrad.gamesgallery.R


@BindingAdapter("imageProfileUrl")
fun ImageView.loadImage(url: String?) {
    if (!url.isNullOrBlank()) {
        Glide.with(this.context)
            .load(url)
            .into(this)
    } else {
        this.setImageResource(R.drawable.ic_profile_black_24)
    }
}