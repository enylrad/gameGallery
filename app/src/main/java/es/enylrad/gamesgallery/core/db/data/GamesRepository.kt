package es.enylrad.gamesgallery.core.db.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.paging.PagedList
import androidx.paging.toLiveData
import es.enylrad.gamesgallery.core.db.GamesDao
import es.enylrad.gamesgallery.core.db.resultLiveData
import es.enylrad.gamesgallery.core.model.GameEntity
import kotlinx.coroutines.CoroutineScope

/**
 * Repository module for handling data operations.
 */
class GamesRepository(
    private val gamesDao: GamesDao,
    private val gamesRemoteDataSource: GamesRemoteDataSource
) {

    fun observePagedGames(
        connectivityAvailable: Boolean,
        coroutineScope: CoroutineScope
    ): LiveData<PagedList<GameEntity>> {
        return if (connectivityAvailable) {
            observeRemotePagedGames(coroutineScope)
        } else {
            observeLocalPagedGames()
        }
    }

    private fun observeLocalPagedGames(): LiveData<PagedList<GameEntity>> {
        return gamesDao.getGamesPagedList()
            .toLiveData(config = GamesPageDataSourceFactory.pagedListConfig())
    }

    private fun observeRemotePagedGames(ioCoroutineScope: CoroutineScope)
            : LiveData<PagedList<GameEntity>> {
        return GamesPageDataSourceFactory(
            gamesRemoteDataSource,
            gamesDao,
            ioCoroutineScope
        ).toLiveData(config = GamesPageDataSourceFactory.pagedListConfig())
    }

    fun observeGame(id: String) = resultLiveData(
        databaseQuery = { gamesDao.getGame(id) },
        networkCall = { gamesRemoteDataSource.fetchGame(id) },
        saveCallResult = { gamesDao.insert(it) })
        .distinctUntilChanged()

    fun observeGames() = resultLiveData(
        databaseQuery = { gamesDao.getGames() },
        networkCall = { gamesRemoteDataSource.fetchGames(1, PAGE_SIZE) },
        saveCallResult = { gamesDao.insertAll(it) })

    companion object {

        const val PAGE_SIZE = 100

        private var instance: GamesRepository? = null

        fun getInstance(gamesDao: GamesDao, gamesRemoteDataSource: GamesRemoteDataSource) =
            instance ?: synchronized(this) {
                instance
                    ?: GamesRepository(gamesDao, gamesRemoteDataSource).also { instance = it }
            }
    }
}
