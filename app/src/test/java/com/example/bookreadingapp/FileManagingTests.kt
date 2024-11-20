package com.example.bookreadingapp

import com.example.bookreadingapp.objects.AppViewModel
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.File

class FileManagingTests {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var testFile: File

    // Using MockWebServer to test for http requests
    // https://medium.com/xebia-engineering/the-recommended-way-of-testing-http-calls-mockwebserver-by-okhttp-e716f87d6122
    @Before
    // Initializing the ViewModel before each test
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        testFile = File.createTempFile("test", ".tmp")
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
        testFile.delete()
    }
    
}