package es.enylrad.gamesgallery.commons.model.utils

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import es.enylrad.gamesgallery.commons.model.UserEntity

fun GoogleSignInAccount.createUser(): UserEntity {

    return UserEntity(
        name = displayName,
        image = photoUrl.toString()
    )
}

fun UserEntity.update(userEntity: UserEntity) {
    name = userEntity.name
    image = userEntity.image
    friends = userEntity.friends
}