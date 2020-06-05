package es.enylrad.gamesgallery.core.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class GamesServiceTest {
    @get:Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: IGDBService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IGDBService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun requestGames() {
        runBlocking {
            enqueueResponse("games.json")
            val filter =
                "fields id, name, summary, cover.image_id, created_at, updated_at, screenshots.*, popularity, aggregated_rating, first_release_date, total_rating, storyline, status, category; sort popularity desc; limit 50; offset 0;"
            val resultResponse = service.getGames(filter).body()

            val request = mockWebServer.takeRequest()
            assertNotNull(resultResponse)
            assertThat(request.path, `is`("/games"))
        }
    }

    @Test
    fun getGameItem() {
        runBlocking {
            enqueueResponse("games.json")
            val filter =
                "fields id, name, summary, cover.image_id, created_at, updated_at, screenshots.*, popularity, aggregated_rating, first_release_date, total_rating, storyline, status, category; sort popularity desc; limit 50; offset 0;"

            val resultResponse = service.getGames(filter).body()
            val games = resultResponse!!

            val game = games[0]
            assertThat(game.id, `is`(115278))
            assertThat(game.aggregated_rating, `is`(86.6666666666667))
            assertThat(game.category, `is`(0))
            assertThat(game.created_at, `is`(1550016000.toLong()))
            assertThat(game.first_release_date, `is`(1564012800.toLong()))
            assertThat(game.name, `is`("Rune Factory 4 Special"))
            assertThat(game.popularity, `is`(2090.622837928576))
            assertThat(
                game.summary,
                `is`("Experience this legendary fantasy adventure like never before and embark on exciting escapades with your favorite characters in the brand new Newlywed Mode!")
            )
            assertThat(game.total_rating, `is`(86.6666666666667))
            assertThat(game.updated_at, `is`(1591315200.toLong()))
        }
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse.setBody(
                source.readString(Charsets.UTF_8)
            )
        )
    }
}
