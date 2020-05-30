package es.enylrad.gamesgallery.core.api

import es.enylrad.gamesgallery.core.model.GameEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Class for Management of ApiService Request/Response
 */
interface IGDBService {

    @POST("/games")
    suspend fun getGames(@Body filter: String): Response<List<GameEntity>>

    @POST("/games")
    suspend fun getGame(@Body filter: String): Response<GameEntity>
}