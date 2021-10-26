package ie.wit.readnote.main

import android.app.Application
import ie.wit.readnote.models.*
import timber.log.Timber

class readNoteApp : Application() {

    lateinit var data : DataJSONStore

//    val books = BookMemStore()


    override fun onCreate() {
        super.onCreate()
        data = DataJSONStore(applicationContext)
        Timber.plant(Timber.DebugTree())
        Timber.i("Starting ReadNote Application")
    }
}