package es.enylrad.gamesgallery.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.enylrad.gamesgallery.commons.model.GameEntity
import es.enylrad.gamesgallery.commons.network.ApiService
import es.enylrad.gamesgallery.commons.utils.callbackResponse

class DashboardViewModel(apiService: ApiService) : ViewModel() {
    var selected: MutableLiveData<GameEntity>? = null

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

    val games: LiveData<MutableList<GameEntity>> = _games

    fun onItemClick(index: Int?) {
        val db: GameEntity? = getGameAt(index)
        selected?.value = db
    }

    fun getGameAt(index: Int?): GameEntity? {
        val gamesValue = games.value
        return if (gamesValue != null && index != null && gamesValue.size > index) {
            gamesValue[index]
        } else {
            null
        }
    }
}