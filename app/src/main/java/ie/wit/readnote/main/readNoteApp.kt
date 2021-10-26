package ie.wit.readnote.main

import android.app.Application
import ie.wit.readnote.models.*
import timber.log.Timber

class readNoteApp : Application() {

    lateinit var books : BookJSONStore

//    val books = BookMemStore()


    override fun onCreate() {
        super.onCreate()
        books = BookJSONStore(applicationContext)
        Timber.plant(Timber.DebugTree())
        Timber.i("Starting ReadNote Application")
    }
}