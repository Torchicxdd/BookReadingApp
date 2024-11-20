package com.example.bookreadingapp.download

import android.content.Context
import android.os.Environment
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.mockito.kotlin.*
import java.io.File

class FileUnzippingTests {
    private var contextMock: Context = mock()
    private lateinit var fileDownload: FileDownload
    private lateinit var zipFileMock: File
    private lateinit var unzipDestinationFolder: File

    @Before
    fun setup() {
        // Setup the fake zip file and destination folder
        zipFileMock = File.createTempFile("test", ".zip")
        unzipDestinationFolder = File("mock/destination/folder")
        unzipDestinationFolder.mkdirs()

        // Mock context to return the destination folder path
        whenever(contextMock.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS))
            .thenReturn(unzipDestinationFolder)

        // Initialize FileDownload
        fileDownload = FileDownload(contextMock)
    }

    @After
    fun tearDown() {
        zipFileMock.delete()
        unzipDestinationFolder.deleteRecursively()
    }

    @Test
    fun unzipFile_returnsValidPath() = runBlocking {
        // Creating a fake zip entry for the HTML file
        val fakeHtmlFile = File(unzipDestinationFolder, "index.html")

        // Simulate unzipping by creating a fake HTML file
        fakeHtmlFile.createNewFile()

        val unzippedFilePath = fileDownload.unzipFile(zipFileMock, "testFolder")

        // Assert that the returned path exists and points to a valid file
        val unzippedFile = File(unzippedFilePath)
        assertTrue("The unzipped file does not exist at the expected path", unzippedFile.exists())
        assertTrue("The unzipped file is not an HTML file", unzippedFile.name.endsWith(".html"))
    }
}
