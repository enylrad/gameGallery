package es.enylrad.gamesgallery.core.model.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import es.enylrad.gamesgallery.R
import es.enylrad.gamesgallery.core.model.CoverEntity
import es.enylrad.gamesgallery.core.sealed.CoverSize


@BindingAdapter("gameCover", "gameScreens")
fun ImageView.loadImageGame(cover: CoverEntity?, screenShots: List<CoverEntity>?) {

    if (cover?.image_id != null) {
        setImageIGDB(cover.image_id)
        return
    } else if (screenShots != null && screenShots.isNotEmpty()) {
        for (image in screenShots) {
            if (!image.image_id.isNullOrBlank()) {
                setImageIGDB(image.image_id)
                return
            }
        }
    }

    setImageResource(R.drawable.default_cover)

}

private fun ImageView.setImageIGDB(imageId: String) {
    val size = CoverSize.CoverBig().id
    val url = "https://images.igdb.com/igdb/image/upload/t_$size/${imageId}.jpg"

    Glide.with(context)
        .load(url)
        .into(this)
}