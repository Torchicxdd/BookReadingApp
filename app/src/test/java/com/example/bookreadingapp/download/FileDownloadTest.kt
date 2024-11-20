package com.example.bookreadingapp.download

import android.content.Context
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.mockito.kotlin.mock
import java.io.File

class FileDownloadTest {

    private var contextMock: Context = mock()
    private lateinit var mockWebServer: MockWebServer
    private lateinit var testFile: File
    private lateinit var fileDownload: FileDownload

    // Using MockWebServer to test for http requests
    // https://medium.com/xebia-engineering/the-recommended-way-of-testing-http-calls-mockwebserver-by-okhttp-e716f87d6122
    @Before
    // Initializing the ViewModel before each test
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        testFile = File.createTempFile("test", ".tmp")
        fileDownload = FileDownload(contextMock)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
        testFile.delete()
    }

    @Test
    fun downloadFiles_downloadSuccessfully() = runTest {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("fake content")
        mockWebServer.enqueue(mockResponse)

        val url = mockWebServer.url("/").toString()
        val result = fileDownload.downloadFile(url, testFile)

        // Test that true is returned and that file exists with content
        assertTrue(result)
        assertTrue(testFile.exists())
        assertTrue(testFile.readText() == "fake content")
    }

    @Test
    fun downloadFiles_downloadFailedWithErrorCode() = runTest {
        val mockResponse = MockResponse()
            .setResponseCode(404)
        mockWebServer.enqueue(mockResponse)

        val url = mockWebServer.url("/").toString()
        val result = fileDownload.downloadFile(url, testFile)

        // Test that false is returned and length of file is 0
        assertFalse(result)
        assertTrue(testFile.length() == 0L)
    }

    @Test
    fun downloadFiles_dowloadFailedWithEmptyResponseBody() = runTest {
        val mockResponse = MockResponse()
            .setResponseCode(204)
            .setBody("")
        mockWebServer.enqueue(mockResponse)

        val url = mockWebServer.url("/").toString()
        val result = fileDownload.downloadFile(url, testFile)

        // Test that false is returned and length of file is 0
        assertFalse(result)
        assertTrue(testFile.length() == 0L)
    }

}