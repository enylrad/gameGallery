package es.enylrad.gamesgallery.commons.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GameEntity(
    val id: String? = null,
    val name: String? = null,
    val summary: String? = null,
    val cover: CoverEntity? = null,
    val created_at: Long? = null,
    val updated_at: Long? = null,
    val screenshots: List<CoverEntity>? = null
) : Parcelable