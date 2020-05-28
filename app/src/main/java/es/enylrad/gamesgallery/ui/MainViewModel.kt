package es.enylrad.gamesgallery.ui

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import es.enylrad.gamesgallery.commons.model.UserEntity
import es.enylrad.gamesgallery.commons.tag.USERS
import timber.log.Timber

class MainViewModel(
    private val firestore: FirebaseFirestore,
    sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _user = MutableLiveData<UserEntity>()

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

    fun updateUser(userEntity: UserEntity) {
        _user.apply {
            value = userEntity
        }
    }
}