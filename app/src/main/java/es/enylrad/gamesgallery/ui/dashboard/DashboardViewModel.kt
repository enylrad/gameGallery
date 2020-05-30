package es.enylrad.gamesgallery.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.enylrad.gamesgallery.core.db.data.GamesRepository
import es.enylrad.gamesgallery.core.model.GameEntity
import es.enylrad.gamesgallery.core.sealed.AdapterGameType
import kotlinx.coroutines.CoroutineScope

class DashboardViewModel(
    private val repository: GamesRepository,
    private val ioCoroutineScope: CoroutineScope
) :
    ViewModel() {

    var connectivityAvailable: Boolean = false

    val games by lazy {
        repository.observePagedGames(connectivityAvailable, ioCoroutineScope)
    }

    private fun getGameAt(index: Int): GameEntity? {
        val gamesValue = games.value
        return if (gamesValue != null && gamesValue.size > index) {
            gamesValue[index]
        } else {
            null
        }
    }

    private val _gameAdapter = MutableLiveData<AdapterGameType>().apply {
        AdapterGameType.GridGameAdapter()
    }

    val gameAdapter: MutableLiveData<AdapterGameType> = _gameAdapter

    fun changeViewList(type: Int) {
        when (type) {
            AdapterGameType.CardGameAdapter().type -> {
                _gameAdapter.value = AdapterGameType.CardGameAdapter()
            }
            AdapterGameType.SimpleGameAdapter().type -> {
                _gameAdapter.value = AdapterGameType.SimpleGameAdapter()
            }
            AdapterGameType.GridGameAdapter().type -> {
                _gameAdapter.value = AdapterGameType.GridGameAdapter()
            }
        }
    }

}