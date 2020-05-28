package es.enylrad.gamesgallery.ui

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import es.enylrad.gamesgallery.commons.model.UserEntity

class MainViewModel(
    firestore: FirebaseFirestore,
    sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _user = MutableLiveData<UserEntity>()

    val user: LiveData<UserEntity> = _user

    fun updateUser(userEntity: UserEntity) {
        _user.apply {
            value = userEntity
        }
    }

}