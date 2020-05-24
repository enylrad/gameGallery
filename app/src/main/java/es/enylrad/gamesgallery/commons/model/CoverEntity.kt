package es.enylrad.gamesgallery.commons.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CoverEntity(
    val id: Int? = null,
    val game: Int? = null,
    val height: Int? = null,
    val image_id: String? = null,
    val url: String? = null,
    val width: Int? = null
) : Parcelable