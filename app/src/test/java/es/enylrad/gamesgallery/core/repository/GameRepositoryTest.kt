package es.enylrad.gamesgallery.core.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import es.enylrad.gamesgallery.core.api.IGDBService
import es.enylrad.gamesgallery.core.db.AppDatabase
import es.enylrad.gamesgallery.core.db.GamesDao
import es.enylrad.gamesgallery.core.db.data.GamesRemoteDataSource
import es.enylrad.gamesgallery.core.db.data.GamesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class GameRepositoryTest {
    private lateinit var repository: GamesRepository
    private val dao = mock(GamesDao::class.java)
    private val service = mock(IGDBService::class.java)
    private val remoteDataSource = GamesRemoteDataSource(service, null)
    private val mockRemoteDataSource = spy(remoteDataSource)

    @get:Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Before
    fun init() {
        val db = mock(AppDatabase::class.java)
        `when`(db.gamesDAO()).thenReturn(dao)
        `when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        repository = GamesRepository(dao, remoteDataSource)
    }

    @Test
    fun loadGamesFromNetwork() {
        runBlocking {
            repository.observePagedGames(
                connectivityAvailable = true,
                coroutineScope = coroutineScope
            )

            verify(dao, never()).getGames()
            verifyZeroInteractions(dao)
        }
    }

}