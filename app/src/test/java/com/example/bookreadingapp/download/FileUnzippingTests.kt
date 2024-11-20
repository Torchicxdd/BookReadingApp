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

/**
 * Unit test class for testing the FileUnzipping functionality of the FileDownload class.
 */
class FileUnzippingTests {

    private var contextMock: Context = mock()

    private lateinit var zipFileMock: File
    private lateinit var unzipDestinationFolder: File

    private lateinit var fileDownload: FileDownload

    /**
     * Helper function to create a ZIP file from a list of files.
     * This function will take a list of files and compress them into a single ZIP file.
     *
     * The following code is taken from
     * https://www.baeldung.com/kotlin/zip-file#:~:text=First%2C%20we%20open%20a%20ZipOutputStream,file%20in%20the%20ZIP%20file
     *
     * @param files The list of files to be compressed into a ZIP file.
     * @param outputZipFile The destination file where the ZIP will be saved.
     * @return The output ZIP file.
     */
    private fun createZipFile(files: List<File>, outputZipFile: File): File {
        // Use ZipOutputStream to create a ZIP file
        ZipOutputStream(FileOutputStream(outputZipFile)).use { zipOut ->
            files.forEach { file ->
                // For each file, create a ZipEntry and copy the file into the ZIP
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

    /**
     * Setup method that is called before each test.
     * Initializes the mock context, creates a temporary zip file, and prepares the test environment.
     */
    @Before
    fun setup() {
        // Set up a mock folder to unzip files into
        unzipDestinationFolder = File("mock/destination/folder")
        unzipDestinationFolder.mkdirs()
        fileDownload = FileDownload(contextMock)

        // Making a fake HTML file that will be used in the ZIP file
        val fakeHtmlFile = File(unzipDestinationFolder, "index.html")
        fakeHtmlFile.createNewFile()

        // Making a mock zip file containing the fake HTML file
        zipFileMock = File.createTempFile("test", ".zip")
        createZipFile(listOf(fakeHtmlFile), zipFileMock)
    }

    /**
     * Cleanup method that is called after each test.
     * This method deletes the temporary files created during the test to ensure a clean state.
     */
    @After
    fun tearDown() {
        // Deleting the created zip file and destination folder after the test
        zipFileMock.delete()
        unzipDestinationFolder.delete()
    }

    /**
     * Test method to verify that the unzipFile method correctly extracts files from the zip archive.
     * It ensures that the unzipped file exists and is an HTML file.
     */
    @Test
    fun unzipFile_returnsValidPath() = runTest {
        val unzippedFilePath = fileDownload.unzipFile(zipFileMock, "testFolder")

        val unzippedFile = File(unzippedFilePath)
        assertTrue("The unzipped file does not exist at the expected path", unzippedFile.exists())
        assertTrue("The unzipped file is not an HTML file", unzippedFile.name.endsWith(".html"))
    }
}
