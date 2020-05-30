package es.enylrad.gamesgallery.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import es.enylrad.gamesgallery.core.constants.USERS
import es.enylrad.gamesgallery.core.model.UserEntity
import es.enylrad.gamesgallery.core.model.utils.update
import timber.log.Timber

class MainViewModel(
    private val firestore: FirebaseFirestore,
    private val userEntity: UserEntity
) : ViewModel() {

    private val _user = MutableLiveData<UserEntity>().apply {
        userEntity
    }

    val user: LiveData<UserEntity> = _user

    fun createUser(userEntity: UserEntity, uid: String) {
        firestore
            .collection(USERS)
            .document(uid)
            .set(userEntity, SetOptions.merge())
            .addOnSuccessListener {
                Timber.d("Create user: Success")
            }
            .addOnCanceledListener {
                Timber.d("Create user: Cancel")
            }
            .addOnFailureListener { exception ->
                Timber.d(exception)
            }
    }

    fun refreshUser(user: UserEntity) {
        userEntity.update(user)
        _user.apply {
            value = user
        }
    }
}