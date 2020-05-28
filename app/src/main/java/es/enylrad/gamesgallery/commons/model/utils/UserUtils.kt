package es.enylrad.gamesgallery.commons.model.utils

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import es.enylrad.gamesgallery.commons.model.UserEntity

fun GoogleSignInAccount.createUser(): UserEntity {

    val image = photoUrl.toString().replace("s96-c", "s500-c", true)

    return UserEntity(
        name = displayName,
        image = image
    )
}