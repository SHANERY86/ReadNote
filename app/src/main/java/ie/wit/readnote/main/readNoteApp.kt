package ie.wit.readnote.main

import android.app.Application
import android.view.inputmethod.InputMethodManager
import ie.wit.readnote.models.*
import timber.log.Timber

class readNoteApp : Application() {


    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.i("Starting ReadNote Application")
    }


}