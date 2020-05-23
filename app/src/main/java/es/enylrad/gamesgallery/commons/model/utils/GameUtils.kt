package es.enylrad.gamesgallery.commons.model.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import es.enylrad.gamesgallery.commons.model.ScreenShots


@BindingAdapter("gameCover")
fun loadImageGame(view: ImageView, screenshots: MutableList<ScreenShots>?) {

    var screenShot = ScreenShots()
    for (screenshot in screenshots ?: listOf<ScreenShots>()) {
        if (screenshot.image_id.isNotBlank()) {
            screenShot = screenshot
        }
    }
    if (screenShot.image_id.isNotBlank()) {
        val size = "cover_big"
        val u = "https://images.igdb.com/igdb/image/upload/t_$size/${screenShot.image_id}.jpg"

        Glide.with(view.context)
            .load(u)
            .into(view)
    }

}