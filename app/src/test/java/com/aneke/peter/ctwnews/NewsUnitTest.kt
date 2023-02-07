package com.aneke.peter.ctwnews

import com.aneke.peter.ctwnews.network.RetrofitClient
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class NewsUnitTest {

    val apiService = RetrofitClient.makeApiService()
    val testApiKey = "564ebf9dfec4409fbdaf1327e47c1932"
    val bbcSource = "bbc-news"
    val aljazzSource = "al-jazeera-english"
    val cnnSource = "cnn"
    val apSource = "associated-press"
    val buzzSource = "buzzfeed"

    @Test
    fun testHeadlinesEndPoint() {
        runTest {
            val result = apiService.getHeadlines(bbcSource, testApiKey)
            assert(result.success)
        }
    }

    @Test
    fun testBBCSource() {
        runTest {
            val result = apiService.getHeadlines(bbcSource, testApiKey)
            assertEquals(result.articles.random().source.id, bbcSource)
        }
    }

    @Test
    fun testAPSource() {
        runTest {
            val result = apiService.getHeadlines(apSource, testApiKey)
            assertEquals(result.articles.random().source.id, apSource)
        }
    }

    @Test
    fun testCNNSource() {
        runTest {
            val result = apiService.getHeadlines(cnnSource, testApiKey)
            assertEquals(result.articles.random().source.id, cnnSource)
        }
    }

    @Test
    fun testAlJazzSource() {
        runTest {
            val result = apiService.getHeadlines(aljazzSource, testApiKey)
            assertEquals(result.articles.random().source.id, aljazzSource)
        }
    }

    @Test
    fun testBuzzSource() {
        runTest {
            val result = apiService.getHeadlines(buzzSource, testApiKey)
            assertEquals(result.articles.random().source.id, buzzSource)
        }
    }

    @Test
    fun testArticlesAreReturned() {
        runTest {
            val result = apiService.getHeadlines("bbc-news", testApiKey)
            assertTrue(result.success)
            assertTrue(result.articles.isNotEmpty())
        }
    }

}