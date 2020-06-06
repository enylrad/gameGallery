package es.enylrad.gamesgallery.core.db.data

import android.content.Context
import es.enylrad.gamesgallery.core.api.BaseDataSource
import es.enylrad.gamesgallery.core.api.IGDBService
import es.enylrad.gamesgallery.core.db.Result
import es.enylrad.gamesgallery.core.model.GameEntity
import es.enylrad.gamesgallery.testing.OpenForTesting

/**
 * Works with the IGDB API to get data.
 */
@OpenForTesting
class GamesRemoteDataSource(private val service: IGDBService, context: Context?) :
    BaseDataSource(context) {

    suspend fun fetchGames(page: Int, pageSize: Int? = null): Result<List<GameEntity>> {
        val filter =
            "fields id, name, summary, cover.image_id, created_at, updated_at, screenshots.*, popularity, aggregated_rating, first_release_date, total_rating, storyline, status, category; sort popularity desc; limit $pageSize; offset $page;"
        return getResult { service.getGames(filter) }
    }


    suspend fun fetchGame(id: Int): Result<GameEntity> {
        val filter =
            "fields id, name, summary, cover.image_id, created_at, updated_at, screenshots.*; where id = $id"
        return getResult { service.getGame(filter) }
    }
}
