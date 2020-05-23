package es.enylrad.gamesgallery.commons.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Class for Management of ApiService Request/Response
 */
interface ApiService {

    @POST("/games")
    fun getGames(@Body body: String = "fields *; limit 10;"): Call<String>
}