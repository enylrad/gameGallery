package es.enylrad.gamesgallery.core.model.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import es.enylrad.gamesgallery.R
import es.enylrad.gamesgallery.core.model.CoverEntity
import es.enylrad.gamesgallery.core.sealed.CoverSize


@BindingAdapter("gameCover", "gameScreens", "gameCoverSize", "gameCoverByTwo")
fun ImageView.loadImageGame(
    cover: CoverEntity?,
    screenShots: List<CoverEntity>?,
    size: CoverSize,
    coverByTwo: Boolean
) {

    if (cover?.image_id != null) {
        setImageIGDB(cover.image_id, size, coverByTwo)
        return
    } else if (screenShots != null && screenShots.isNotEmpty()) {
        for (image in screenShots) {
            if (!image.image_id.isNullOrBlank()) {
                setImageIGDB(image.image_id, size, coverByTwo)
                return
            }
        }
    }

    setImageResource(R.drawable.default_cover)

}

private fun ImageView.setImageIGDB(
    imageId: String,
    size: CoverSize,
    coverByTwo: Boolean
) {
    val url = if (coverByTwo) {
        "https://images.igdb.com/igdb/image/upload/t_${size.name}/${imageId}.jpg"
    } else {
        "https://images.igdb.com/igdb/image/upload/t_${size.name}_2x/${imageId}.jpg"
    }

    Glide.with(context)
        .load(url)
        .into(this)
}