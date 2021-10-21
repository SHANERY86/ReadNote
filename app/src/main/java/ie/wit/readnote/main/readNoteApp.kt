package ie.wit.readnote.main

import android.app.Application
import ie.wit.readnote.models.BookMemStore
import ie.wit.readnote.models.NoteMemStore
import ie.wit.readnote.models.NoteStore
import timber.log.Timber

class readNoteApp : Application() {

    val notes = NoteMemStore()
    val books = BookMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.i("Starting ReadNote Application")
    }
}