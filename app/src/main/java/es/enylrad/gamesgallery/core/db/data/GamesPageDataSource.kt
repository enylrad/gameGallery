package es.enylrad.gamesgallery.core.db.data


import androidx.paging.PageKeyedDataSource
import es.enylrad.gamesgallery.commons.utils.reportCrash
import es.enylrad.gamesgallery.core.constants.PAGE_SIZE
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

    companion object {
        const val TAG = "GamesPageDataSource"
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GameEntity>
    ) {
        fetchData(0, params.requestedLoadSize) {
            callback.onResult(it, null, PAGE_SIZE)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GameEntity>) {
        val page = params.key
        fetchData(page, params.requestedLoadSize) {
            callback.onResult(it, page + PAGE_SIZE)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GameEntity>) {
        val page = params.key
        fetchData(page, params.requestedLoadSize) {
            callback.onResult(it, page - PAGE_SIZE)
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

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, t ->
        reportCrash(t, TAG)
        postError(t.message ?: t.toString())
    }

    private fun postError(message: String) {
        Timber.e("An error happened: $message")
        // TODO
    }

}