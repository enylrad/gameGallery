package es.enylrad.gamesgallery.commons.network

import es.enylrad.gamesgallery.commons.model.GameEntity
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Class for Management of ApiService Request/Response
 */
interface ApiService {

    @POST("/games")
    fun getGames(@Body body: String = "fields id, name, summary, created_at, updated_at, screenshots.*; limit 10;"): Call<MutableList<GameEntity>>
}