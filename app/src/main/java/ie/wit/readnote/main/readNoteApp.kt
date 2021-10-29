package ie.wit.readnote.main

import android.app.Application
import android.content.Intent
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import ie.wit.readnote.R
import ie.wit.readnote.activities.Account
import ie.wit.readnote.activities.Book
import ie.wit.readnote.activities.BookList
import ie.wit.readnote.activities.Login
import ie.wit.readnote.models.*
import timber.log.Timber

class readNoteApp : Application() {

    lateinit var data: DataJSONStore
    var loggedInUser = UserModel()
    lateinit var imm: InputMethodManager

    //    val books = BookMemStore()
    override fun onCreate() {
        super.onCreate()
        data = DataJSONStore(applicationContext)
        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        Timber.plant(Timber.DebugTree())
        Timber.i("Starting ReadNote Application")
    }

    fun setUser(user: UserModel) {
        loggedInUser = user
    }

    fun logOut() {
        loggedInUser = UserModel()
    }

    fun getMenuOptions(a: AppCompatActivity,item: MenuItem)  : Boolean {
        return when (item.itemId) {
            R.id.action_addbook -> {
                val intent = Intent(this, Book::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                true
            }
            R.id.action_booklist -> {
                val intent = Intent(this, BookList::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                true
            }
            R.id.action_account -> {
                val intent = Intent(this, Account::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                true
            }
            R.id.action_logout -> {
                logOut()
                val intent = Intent(this, Login::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                true
            }
            else -> a.onOptionsItemSelected(item)
        }
    }
}