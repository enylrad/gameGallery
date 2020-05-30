package es.enylrad.gamesgallery.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import es.enylrad.gamesgallery.core.model.UserEntity

class ProfileViewModel(firestore: FirebaseFirestore, userEntity: UserEntity) : ViewModel() {

    private val _user = MutableLiveData<UserEntity>().apply {
        value = userEntity
    }

    val user: LiveData<UserEntity> = _user

}