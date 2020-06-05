package es.enylrad.gamesgallery.core.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import es.enylrad.gamesgallery.core.db.data.GamesRepository
import es.enylrad.gamesgallery.ui.dashboard.DashboardViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class GameViewModelTest {

    @get:Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val repository = mock(GamesRepository::class.java)
    private var viewModel = DashboardViewModel(repository, coroutineScope)

    @Test
    fun testNull() {
        assertThat(viewModel.connectivityAvailable, notNullValue())
        verify(repository, never()).observePagedGames(false, coroutineScope)
        verify(repository, never()).observePagedGames(true, coroutineScope)
    }

    @Test
    fun doNotFetchWithoutObservers() {
        verify(repository, never()).observePagedGames(false, coroutineScope)
    }

    @Test
    fun doNotFetchWithoutObserverOnConnectionChange() {
        viewModel.connectivityAvailable = true

        verify(repository, never()).observePagedGames(true, coroutineScope)
    }

}