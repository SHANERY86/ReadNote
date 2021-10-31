package org.wit.readnote.helpers

import android.content.Context
import timber.log.Timber.e
import timber.log.Timber.i
import java.io.*

fun write(context: Context, fileName: String, data: String) {
    if(!fileName.equals("test")) {
        try {
            val outputStreamWriter =
                OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE))
            outputStreamWriter.write(data)
            outputStreamWriter.close()
        } catch (e: Exception) {
            e("Cannot read file: %s", e.toString())
        }
    }
}

fun read(context: Context, fileName: String): String {
    var str = ""
    if(fileName.contains("test")){
        val jsonFile = File("src/test/java/ie/wit/readnote/testdata.json")
        val inputStream = jsonFile.inputStream()
        inputStream.bufferedReader().forEachLine { str += it }
    }
    else {
        try {
            val inputStream = context.openFileInput(fileName)
            if (inputStream != null) {
                val inputStreamReader = InputStreamReader(inputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                val partialStr = StringBuilder()
                var done = false
                while (!done) {
                    val line = bufferedReader.readLine()
                    done = (line == null)
                    if (line != null) partialStr.append(line)
                }
                inputStream.close()
                str = partialStr.toString()
            }
        } catch (e: FileNotFoundException) {
            e("file not found: %s", e.toString())
        } catch (e: IOException) {
            e("cannot read file: %s", e.toString())
        }
    }
    return str
}

fun exists(context: Context, filename: String): Boolean {
    val file = context.getFileStreamPath(filename)
    return file.exists()
}