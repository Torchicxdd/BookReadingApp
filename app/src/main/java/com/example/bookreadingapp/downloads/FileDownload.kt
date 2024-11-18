package com.example.bookreadingapp.downloads

import android.content.Context
import android.os.Environment
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.zip.ZipFile

private val TAG = "FileDownload"

class FileDownload(private val context: Context) {
    // Create download folder if it doesn't exist, then saves file
    fun createFile(directoryName: String, fileName: String): File {
        val downloadFolder = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), directoryName)
        if (!downloadFolder.exists()) downloadFolder.mkdirs()
        downloadFolder.listFiles()?.forEach { file ->
            if(file.exists()) {
                try {
                    ExtractFile.unzipFile(file, "unzipped", context)
                } catch(e: IOException) {
                    e.printStackTrace()
                    e.message?.let { Log.e(TAG, it) }
                }
            }
        }

        return File(downloadFolder, fileName)
    }

    // Retrieve download folders
    fun listDirectoryContents(directoryName: String): List<String> {
        val downloadFolder = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), directoryName)
        return downloadFolder.listFiles()?.map { it.name } ?: emptyList()
    }

    // Request content from url and saves to file location
    fun downloadFile(url: String, file: File): Boolean {
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
    fun deleteDirectoryContents(directoryName: String) {
        val downloadFolder = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), directoryName)
        downloadFolder.listFiles()?.forEach { it.delete() }
    }
}

object ExtractFile {
    private const val BUFFER_SIZE = 4096

    private fun extractFile(inputStream: InputStream, destFilePath: String) {
        val bos = BufferedOutputStream(FileOutputStream(destFilePath))
        val bytesIn = ByteArray(BUFFER_SIZE)
        var read: Int
        while (inputStream.read(bytesIn).also { read = it } != -1) {
            bos.write(bytesIn, 0, read)
        }
        bos.close()
    }

    fun unzipFile(zipFile: File, destinationDirectory: String, context: Context) {
        val unzipFolder = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), destinationDirectory)
        if (!unzipFolder.exists()) {
            unzipFolder.mkdirs()
        }
        Log.i(TAG, unzipFolder.absolutePath)


        ZipFile(zipFile).use { zip ->
            zip.entries().asSequence().forEach { entry ->
                if(entry.name.contains(".html")) {
                    Log.i(TAG, "Entry: $entry")
                    zip.getInputStream(entry).use { input ->
                        val filePath = unzipFolder.absolutePath + File.separator + entry.name

                        if (!entry.isDirectory) {
                            extractFile(input, filePath)
                        } else {
                            val dir = File(filePath)
                            dir.mkdir()
                        }

                        Log.i(TAG, File(filePath).readText())
                    }
                }
            }
        }
    }
}