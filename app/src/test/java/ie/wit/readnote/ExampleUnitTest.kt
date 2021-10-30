package ie.wit.readnote

import android.app.Application
import android.content.Context
import android.net.Uri
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import ie.wit.readnote.helpers.UriParser
import ie.wit.readnote.main.readNoteApp
import ie.wit.readnote.models.BookModel
import ie.wit.readnote.models.DataJSONStore
import ie.wit.readnote.models.UserModel
import org.junit.Test

import org.junit.Assert.*
import java.lang.reflect.Type

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */


class ExampleUnitTest {
    val JSON_FILE = "/data/data.json"
    lateinit var app : readNoteApp
    var data : DataJSONStore = DataJSONStore(app.applicationContext, JSON_FILE)
    @Test
    fun checkData() {

    }

}

