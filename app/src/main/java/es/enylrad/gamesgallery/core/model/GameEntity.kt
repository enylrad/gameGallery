package es.enylrad.gamesgallery.core.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import es.enylrad.gamesgallery.core.db.converters.CoverConverter
import es.enylrad.gamesgallery.core.sealed.CategoryGame
import es.enylrad.gamesgallery.core.sealed.StatusGame
import kotlinx.parcelize.Parcelize

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
    val popularity: Double = 0.0,
    val aggregated_rating: Double = 0.0,
    val first_release_date: Long = 0,
    val total_rating: Double = 0.0,
    val storyline: String? = null,
    val status: Int = StatusGame.Unknown().code,
    val category: Int = CategoryGame.Unknown().code

) : Parcelable