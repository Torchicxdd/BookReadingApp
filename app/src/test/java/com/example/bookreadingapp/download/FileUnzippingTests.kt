package com.example.bookreadingapp.download

import android.content.Context
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.mockito.kotlin.mock
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import java.io.FileInputStream
import java.io.FileOutputStream

class FileUnzippingTests {

    private var contextMock: Context = mock()
    private lateinit var zipFileMock: File
    private lateinit var unzipDestinationFolder: File
    private lateinit var fileDownload: FileDownload

    // Helper function to create a zip file from a list of files
    private fun createZipFile(files: List<File>, outputZipFile: File): File {
        ZipOutputStream(FileOutputStream(outputZipFile)).use { zipOut ->
            files.forEach { file ->
                FileInputStream(file).use { fis ->
                    val zipEntry = ZipEntry(file.name)
                    zipOut.putNextEntry(zipEntry)
                    fis.copyTo(zipOut)
                    zipOut.closeEntry()
                }
            }
        }
        return outputZipFile
    }

    @Before
    fun setup() {
        // Setup the destination folder
        unzipDestinationFolder = File("mock/destination/folder")
        unzipDestinationFolder.mkdirs()

        // Initialize FileDownload with the mock context (or real context if required)
        fileDownload = FileDownload(contextMock)

        // Create a fake HTML file for testing
        val fakeHtmlFile = File(unzipDestinationFolder, "index.html")
        fakeHtmlFile.createNewFile()

        // Create a fake zip file that contains the HTML file
        zipFileMock = File.createTempFile("test", ".zip")
        createZipFile(listOf(fakeHtmlFile), zipFileMock)
    }

    @After
    fun tearDown() {
        zipFileMock.delete()
        unzipDestinationFolder.deleteRecursively()
    }

    @Test
    fun unzipFile_returnsValidPath() = runTest {
        // Unzip the file and get the unzipped file path
        val unzippedFilePath = fileDownload.unzipFile(zipFileMock, "testFolder")

        // Assert that the returned path exists and points to a valid file
        val unzippedFile = File(unzippedFilePath)
        assertTrue("The unzipped file does not exist at the expected path", unzippedFile.exists())
        assertTrue("The unzipped file is not an HTML file", unzippedFile.name.endsWith(".html"))
    }
}
