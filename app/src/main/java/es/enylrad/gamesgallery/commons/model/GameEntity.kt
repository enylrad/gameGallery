package es.enylrad.gamesgallery.commons.model

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GameEntity(
    @PropertyName("id")
    val id: String = "",
    @PropertyName("name")
    val name: String = "",
    @PropertyName("summary")
    var summary: String = "",
    @PropertyName("created_at")
    val created_at: Long = 0,
    @PropertyName("updated_at")
    val updated_at: Long = 0,
    @PropertyName("screenshots")
    val screenshots: List<ScreenShots>? = null
) : Parcelable