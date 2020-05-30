package es.enylrad.gamesgallery.core.db.data

import es.enylrad.gamesgallery.core.api.BaseDataSource
import es.enylrad.gamesgallery.core.api.IGDBService
import es.enylrad.gamesgallery.core.db.Result
import es.enylrad.gamesgallery.core.model.GameEntity

/**
 * Works with the IGDB API to get data.
 */
class GamesRemoteDataSource(private val service: IGDBService) : BaseDataSource() {

    suspend fun fetchGames(page: Int, pageSize: Int? = null): Result<List<GameEntity>> {
        val filter =
            "fields id, name, summary, cover.image_id, created_at, updated_at, screenshots.*, popularity; sort popularity desc; limit $pageSize; offset $page;"
        return getResult { service.getGames(filter) }
    }


    suspend fun fetchGame(id: String): Result<GameEntity> {
        val filter =
            "fields id, name, summary, cover.image_id, created_at, updated_at, screenshots.*; where id = $id"
        return getResult { service.getGame(filter) }
    }
}
