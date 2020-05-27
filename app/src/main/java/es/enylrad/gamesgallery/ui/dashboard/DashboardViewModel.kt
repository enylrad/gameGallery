package es.enylrad.gamesgallery.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.enylrad.gamesgallery.commons.model.GameEntity
import es.enylrad.gamesgallery.commons.network.ApiService
import es.enylrad.gamesgallery.commons.utils.callbackResponse
import es.enylrad.gamesgallery.ui.dashboard.adapter.sealed.GameTypeAdapter

class DashboardViewModel(apiService: ApiService) : ViewModel() {

    private val _games = MutableLiveData<MutableList<GameEntity>>().apply {
        apiService.getGames().enqueue(
            callbackResponse(
                success = {
                    value = it
                },
                fail = {
                    // TODO
                },
                TAG = "getGames"
            )
        )
    }

    private fun getGameAt(index: Int): GameEntity? {
        val gamesValue = games.value
        return if (gamesValue != null && gamesValue.size > index) {
            gamesValue[index]
        } else {
            null
        }
    }

    val games: LiveData<MutableList<GameEntity>> = _games

    private val _selected = MutableLiveData<GameEntity?>()

    val selected: MutableLiveData<GameEntity?> = _selected

    fun onItemClick(index: Int) {
        val db: GameEntity? = getGameAt(index)
        _selected.value = db
    }

    private val _typeAdapter = MutableLiveData<GameTypeAdapter>()

    init {
        _typeAdapter.value = GameTypeAdapter.GridGameAdapter()
    }

    val typeAdapter: MutableLiveData<GameTypeAdapter> = _typeAdapter

    fun changeViewList(type: Int) {
        when (type) {
            GameTypeAdapter.CardGameAdapter().type -> {
                _typeAdapter.value = GameTypeAdapter.CardGameAdapter()
            }
            GameTypeAdapter.SimpleGameAdapter().type -> {
                _typeAdapter.value = GameTypeAdapter.SimpleGameAdapter()
            }
            GameTypeAdapter.GridGameAdapter().type -> {
                _typeAdapter.value = GameTypeAdapter.GridGameAdapter()
            }
        }
    }

}