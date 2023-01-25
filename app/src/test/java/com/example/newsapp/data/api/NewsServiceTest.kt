package com.example.newsapp.data.api

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsServiceTest {
    private lateinit var service: NewsService
    private lateinit var server: MockWebServer

    /**
     * Mock web server will behave as a backend server and reply to the
     * api call made by actual code with mocked response linked to it
     */
    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsService::class.java)
    }

    /**
     * This function will read json file from resource
     * and create mock response
     * This mock response will be set to server and enqueued for the later use
     */
    private fun enqueueMockResponse(fileName: String){
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    @Test
    fun getTopHeadlines_sentRequest_receivedExpected_correctPageSize(){
        runBlocking {
            enqueueMockResponse("newsResponse.json")
            val responseBody = service.getTopHeadlines("in",1).body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/v2/top-headlines?country=in&page=1&apiKey=084c7b4810b0491bb0f6798c77fea01b")
            assertThat(responseBody?.articles?.size).isEqualTo(20)
        }
    }

    @Test
    fun getTopHeadlines_receivedResponse_correctContent(){
        runBlocking {
            enqueueMockResponse("newsResponse.json")
            val responseBody = service.getTopHeadlines("in",1).body()
            assertThat(responseBody).isNotNull()
            val article = responseBody!!.articles[0]
            assertThat(article).isNotNull()
            assertThat(article.title).isNotNull()
            assertThat(article.description).isNotNull()
            assertThat(article.urlToImage).isNotNull()
        }
    }



    @After
    fun tearDown() {
        server.shutdown()
    }
}