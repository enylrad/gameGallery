package es.enylrad.gamesgallery.core.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import es.enylrad.gamesgallery.core.db.converters.CoverConverter
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "games")
@Parcelize
@TypeConverters(CoverConverter::class)
data class GameEntity(
    @PrimaryKey
    val id: Int = 0,
    val name: String? = null,
    val summary: String? = null,
    @Embedded(prefix = "cover_")
    val cover: CoverEntity? = null,
    val created_at: Long? = null,
    val updated_at: Long? = null,
    val screenshots: List<CoverEntity>? = null,
    val popularity: Double = 0.0
) : Parcelable