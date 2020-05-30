package es.enylrad.gamesgallery.core.db.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import es.enylrad.gamesgallery.core.db.GamesDao
import es.enylrad.gamesgallery.core.model.GameEntity
import kotlinx.coroutines.CoroutineScope

class GamesPageDataSourceFactory(
    private val dataSource: GamesRemoteDataSource,
    private val gamesDao: GamesDao,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, GameEntity>() {

    private val liveData = MutableLiveData<GamesPageDataSource>()

    override fun create(): DataSource<Int, GameEntity> {
        val source = GamesPageDataSource(dataSource, gamesDao, scope)
        liveData.postValue(source)
        return source
    }

    companion object {
        private const val PAGE_SIZE = 25

        fun pagedListConfig() = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()
    }

}