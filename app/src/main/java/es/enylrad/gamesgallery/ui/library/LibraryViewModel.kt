package es.enylrad.gamesgallery.ui.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class LibraryViewModel(firestore: FirebaseFirestore) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {

    }

    val text: LiveData<String> = _text
}