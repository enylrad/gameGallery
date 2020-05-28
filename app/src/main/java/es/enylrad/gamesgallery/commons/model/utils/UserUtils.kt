package es.enylrad.gamesgallery.commons.model.utils

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import es.enylrad.gamesgallery.commons.model.UserEntity

fun GoogleSignInAccount.createUser(): UserEntity {

    return UserEntity(
        name = displayName,
        image = photoUrl.toString()
    )
}