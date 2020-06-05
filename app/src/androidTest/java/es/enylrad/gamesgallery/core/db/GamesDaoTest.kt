package es.enylrad.gamesgallery.core.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import es.enylrad.gamesgallery.commons.utils.getValue
import es.enylrad.gamesgallery.commons.utils.testGameA
import es.enylrad.gamesgallery.commons.utils.testGameB
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GamesDaoTest : DbTest() {
    private lateinit var gamesDao: GamesDao
    private val setA = testGameA.copy()
    private val setB = testGameB.copy()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        gamesDao = db.gamesDAO()

        runBlocking {
            gamesDao.insertAll(listOf(setA, setB))
        }
    }

    @Test
    fun testGetGames() {
        val list = getValue(gamesDao.getGames())
        assertThat(list.size, equalTo(2))

        assertThat(list[0], equalTo(setA))
        assertThat(list[1], equalTo(setB))
    }

    @Test
    fun testGetGame() {
        assertThat(getValue(gamesDao.getGame(setA.id)), equalTo(setA))
    }
}