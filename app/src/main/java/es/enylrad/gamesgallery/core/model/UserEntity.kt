package es.enylrad.gamesgallery.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserEntity(
    var name: String? = null,
    var image: String? = null,
    var friends: List<Int>? = null
) : Parcelable