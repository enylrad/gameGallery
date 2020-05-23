package es.enylrad.gamesgallery.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.enylrad.gamesgallery.commons.network.ApiService
import es.enylrad.gamesgallery.commons.utils.callbackResponse

class DashboardViewModel(apiService: ApiService) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        apiService.getGames().enqueue(
            callbackResponse(
                success = {

                    value = it
                },
                fail = {

                },
                TAG = "games"
            )
        )
    }
    val text: LiveData<String> = _text
}