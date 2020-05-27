package es.enylrad.gamesgallery.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import es.enylrad.gamesgallery.commons.model.GameEntity
import es.enylrad.gamesgallery.commons.tag.GAMES
import timber.log.Timber

class ProfileViewModel(firestore: FirebaseFirestore) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        firestore
            .collection(GAMES)
            .get()
            .addOnSuccessListener { result ->
                val games = mutableListOf<GameEntity>()
                for (document in result) {
                    games.add(document.toObject(GameEntity::class.java))
                }
                Timber.d(games.toString())
                value = games.toString()
            }

    }

    val text: LiveData<String> = _text
}