package es.enylrad.gamesgallery.core.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import es.enylrad.gamesgallery.R


@BindingAdapter("imageProfileUrl")
fun ImageView.loadImage(url: String?) {
    if (!url.isNullOrBlank()) {
        val urlSize = url.replace("s96-c", "s500-c", true)
        Glide.with(context)
            .load(urlSize)
            .into(this)
    } else {
        this.setImageResource(R.drawable.ic_profile_black_24)
    }
}