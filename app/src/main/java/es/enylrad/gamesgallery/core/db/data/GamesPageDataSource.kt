package es.enylrad.gamesgallery.core.db.data


import androidx.paging.PageKeyedDataSource
import es.enylrad.gamesgallery.core.db.GamesDao
import es.enylrad.gamesgallery.core.db.Result
import es.enylrad.gamesgallery.core.model.GameEntity
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Data source for games sets pagination via paging library
 */
class GamesPageDataSource(
    private val dataSource: GamesRemoteDataSource,
    private val gamesDao: GamesDao,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, GameEntity>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GameEntity>
    ) {
        fetchData(1, params.requestedLoadSize) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GameEntity>) {
        val page = params.key
        fetchData(page, params.requestedLoadSize) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GameEntity>) {
        val page = params.key
        fetchData(page, params.requestedLoadSize) {
            callback.onResult(it, page - 1)
        }
    }

    private fun fetchData(page: Int, pageSize: Int, callback: (List<GameEntity>) -> Unit) {
        scope.launch(getJobErrorHandler()) {
            val response = dataSource.fetchGames(page, pageSize)
            if (response.status == Result.Status.SUCCESS) {
                val results = response.data!!
                gamesDao.insertAll(results)
                callback(results)
            } else if (response.status == Result.Status.ERROR) {
                postError(response.message!!)
            }
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        postError(e.message ?: e.toString())
    }

    private fun postError(message: String) {
        Timber.e("An error happened: $message")
        // TODO network error handling
        //networkState.postValue(NetworkState.FAILED)
    }

}