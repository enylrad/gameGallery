package es.enylrad.gamesgallery.commons.model

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ScreenShots(
    @PropertyName("image_id")
    val image_id: String = ""
) : Parcelable