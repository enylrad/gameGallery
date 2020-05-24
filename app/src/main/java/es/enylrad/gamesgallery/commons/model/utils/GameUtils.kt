package es.enylrad.gamesgallery.commons.model.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import es.enylrad.gamesgallery.R
import es.enylrad.gamesgallery.commons.model.CoverEntity


@BindingAdapter("gameCover", "gameScreens")
fun loadImageGame(view: ImageView, cover: CoverEntity?, screenShots: List<CoverEntity>?) {

    if (cover?.image_id != null) {
        setImageIGDB(cover.image_id, view)
        return
    } else if (screenShots != null && screenShots.isNotEmpty()) {
        for (image in screenShots) {
            if (!image.image_id.isNullOrBlank()) {
                setImageIGDB(image.image_id, view)
                return
            }
        }
    }

    view.setImageResource(R.drawable.default_cover)

}

private fun setImageIGDB(imageId: String, view: ImageView) {
    val size = "cover_big"
    val url = "https://images.igdb.com/igdb/image/upload/t_$size/${imageId}.jpg"

    Glide.with(view.context)
        .load(url)
        .into(view)
}