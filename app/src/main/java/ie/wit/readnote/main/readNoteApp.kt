package ie.wit.readnote.main

import android.app.Application
import android.view.inputmethod.InputMethodManager
import ie.wit.readnote.models.*
import timber.log.Timber

class readNoteApp : Application() {

    var loggedInUser = UserModel()
    lateinit var imm: InputMethodManager

    //    val books = BookMemStore()
    override fun onCreate() {
        super.onCreate()
//        data = DataJSONStore(applicationContext, "data.json")
//        data = DataMemStore()
        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        Timber.plant(Timber.DebugTree())
        Timber.i("Starting ReadNote Application")
    }

    fun setUser(user: UserModel) {
        loggedInUser = user
    }

}