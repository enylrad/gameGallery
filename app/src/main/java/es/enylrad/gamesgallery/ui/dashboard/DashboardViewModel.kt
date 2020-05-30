package es.enylrad.gamesgallery.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.enylrad.gamesgallery.core.db.data.GamesRepository
import es.enylrad.gamesgallery.core.model.GameEntity
import es.enylrad.gamesgallery.ui.dashboard.adapter.sealed.GameTypeAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

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

    private val _selected = MutableLiveData<GameEntity?>()

    val selected: MutableLiveData<GameEntity?> = _selected

    fun onItemClick(index: Int) {
        val db: GameEntity? = getGameAt(index)
        _selected.value = db
    }

    private val _gameAdapter = MutableLiveData<GameTypeAdapter>().apply {
        GameTypeAdapter.GridGameAdapter()
    }

    val gameAdapter: MutableLiveData<GameTypeAdapter> = _gameAdapter

    fun changeViewList(type: Int) {
        when (type) {
            GameTypeAdapter.CardGameAdapter().type -> {
                _gameAdapter.value = GameTypeAdapter.CardGameAdapter()
            }
            GameTypeAdapter.SimpleGameAdapter().type -> {
                _gameAdapter.value = GameTypeAdapter.SimpleGameAdapter()
            }
            GameTypeAdapter.GridGameAdapter().type -> {
                _gameAdapter.value = GameTypeAdapter.GridGameAdapter()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        ioCoroutineScope.cancel()
    }
}