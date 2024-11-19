package com.example.bookreadingapp.download

import android.content.Context
import android.os.Environment
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.zip.ZipFile
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG_FE = "FileExtract"

class FileDownload(private val context: Context) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    // Create download folder if it doesn't exist, then saves file
    suspend fun createFile(directoryName: String, fileName: String): File {
        var downloadFolder: File = File("")
        withContext(Dispatchers.IO) {
            downloadFolder = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), directoryName)
            if (!downloadFolder.exists()) downloadFolder.mkdirs()
        }

        return File(downloadFolder, fileName)
    }

    // List directory contents
    suspend fun listDirectoryContents(directoryName: String): List<String> {
        var folderToRead: File = File("")
        withContext(Dispatchers.IO) {
            folderToRead = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), directoryName)
        }

        return folderToRead.listFiles()?.map { it.name } ?: emptyList()
    }

    // Request content from url and saves to file location
    suspend fun downloadFile(url: String, file: File): Boolean {
        coroutineScope.launch(Dispatchers.Main) {

        }
        try {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()

            // Returns false if network request was unsuccessful
            if (!response.isSuccessful || response.body == null) {
                return false
            }
            response.body!!.byteStream().use { inputStream ->
                FileOutputStream(file).use { outputStream ->
                    copyData(inputStream, outputStream)
                }
            }
            return true
        } catch(e: IOException) {
            e.printStackTrace()
            return false
        }
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
        coroutineScope.launch(Dispatchers.Main) {

        }
        val downloadFolder = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), directoryName)
        downloadFolder.listFiles()?.forEach {
            it.delete()
        }
    }

    // Unzip file and saves to the same directory
    // Returns absolute path of unzipped html file
    suspend fun unzipFile(zipFile: File, directoryName: String) : String {
        coroutineScope.launch(Dispatchers.Main) {

        }
        var unzippedPath = ""
        val unzipFolder = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), directoryName)
        if (!unzipFolder.exists()) {
            unzipFolder.mkdirs()
        }
        // Log where unzipped content will be placed
        Log.i(TAG_FE, unzipFolder.absolutePath)

        ZipFile(zipFile).use { zip ->
            zip.entries().asSequence().forEach { entry ->
                if(entry.name.contains(".html")) {
                    zip.getInputStream(entry).use { input ->
                        val filePath = unzipFolder.absolutePath + File.separator + entry.name

                        if (!entry.isDirectory) {
                            extractFile(input, filePath)
                        } else {
                            val dir = File(filePath)
                            dir.mkdir()
                        }
                        // Need to change to also unzip images after
                        // Log location of the unzipped html file
                        Log.i(TAG_FE, File(filePath).absolutePath + " Exists: " + File(filePath).exists())
                        unzippedPath = File(filePath).absolutePath
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