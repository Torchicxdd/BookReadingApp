package com.example.bookreadingapp.download

import android.content.Context
import android.os.Environment
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.zip.ZipFile

private const val TAG_FE = "FileExtract"

class FileDownload(private val context: Context) {
    // Operations wrapped with withContext(Dispatchers.IO) {} causes the coroutine to switch to
    // the IO dispatcher for IO manipulation/Network request tasks

    // Create download folder if it doesn't exist, then saves file to that folder
    suspend fun createFile(directoryName: String, fileName: String): File {
        var downloadFolder: File
        withContext(Dispatchers.IO) {
            downloadFolder = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), directoryName)
            if (!downloadFolder.exists()) downloadFolder.mkdirs()
        }

        return File(downloadFolder, fileName)
    }

    // Returns a list of a folder's content
    suspend fun listDirectoryContents(directoryName: String): List<String> {
        var folderToRead: File
        withContext(Dispatchers.IO) {
            folderToRead = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), directoryName)
        }

        return folderToRead.listFiles()?.map { it.name } ?: emptyList()
    }

    // Request content from url and saves to file to its own folder created with createFile()
    // Returns a boolean representing if the request was successful
    suspend fun downloadFile(url: String, file: File): Boolean {
        var downloadSuccess : Boolean = false
        withContext(Dispatchers.IO) {
            try {
                val client = OkHttpClient()
                val request = Request.Builder().url(url).build()
                val response = client.newCall(request).execute()

                // Returns false if network request was unsuccessful
                if (!response.isSuccessful || response.body!!.contentLength() == 0L) {
                    return@withContext false
                }
                response.body!!.byteStream().use { inputStream ->
                    FileOutputStream(file).use { outputStream ->
                        copyData(inputStream, outputStream)
                    }
                }
                downloadSuccess = true
            } catch(e: IOException) {
                e.printStackTrace()
            }
        }
        return downloadSuccess
    }

    // Helper methods
    // Copy data from one stream to another
    private fun copyData(input: InputStream, output: FileOutputStream) {
        val buffer = ByteArray(1024)
        var length: Int
        while (input.read(buffer).also { length = it } > 0) {
            output.write(buffer, 0, length)
        }
    }

    // Delete directory contents directly without IntentSender
    suspend fun deleteDirectoryContents(directoryName: String) {
        var folderToDelete : File
        withContext(Dispatchers.IO) {
            folderToDelete = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), directoryName)
            folderToDelete.listFiles()?.forEach {
                it.delete()
            }
        }
    }

    // Unzip file and saves to the same folder
    // Returns absolute path of unzipped html file
    suspend fun unzipFile(zipFile: File, directoryName: String) : String {
        var unzippedPath = ""
        withContext(Dispatchers.IO) {
            val unzipFolder = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), directoryName)
            if (!unzipFolder.exists()) {
                unzipFolder.mkdirs()
            }

            ZipFile(zipFile).use { zip ->
                zip.entries().asSequence().forEach { entry ->
                    zip.getInputStream(entry).use { input ->
                        // Create images directory
                        File(unzipFolder.absolutePath + File.separator + "images").mkdir()
                        val destFilePath = unzipFolder.absolutePath + File.separator + entry.name
                        if (!entry.isDirectory) {
                            extractFile(input, destFilePath)
                        } else {
                            val dir = File(destFilePath)
                            dir.mkdir()
                        }

                        if (entry.name.contains(".html")) {
                            unzippedPath = File(destFilePath).absolutePath
                        }
                    }
                }
            }
        }

        return unzippedPath
    }

    private fun extractFile(inputStream: InputStream, destFilePath: String) {
        val bos = BufferedOutputStream(FileOutputStream(destFilePath))
        val bytesIn = ByteArray(4096) // BUFFER_SIZE = 4096
        var read: Int
        while (inputStream.read(bytesIn).also { read = it } != -1) {
            bos.write(bytesIn, 0, read)
        }
        bos.close()
    }
}