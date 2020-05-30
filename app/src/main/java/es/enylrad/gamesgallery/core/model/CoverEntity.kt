package es.enylrad.gamesgallery.core.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "covers")
@Parcelize
data class CoverEntity(
    val id: Int = 0,
    val game: Int? = null,
    val height: Int? = null,
    val image_id: String? = null,
    val url: String? = null,
    val width: Int? = null
) : Parcelable