package ie.wit.readnote.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import ie.wit.readnote.R
import ie.wit.readnote.databinding.ActivitySignUpBinding
import ie.wit.readnote.main.readNoteApp
import ie.wit.readnote.models.UserModel
import timber.log.Timber
import timber.log.Timber.i

class SignUp : AppCompatActivity() {
    private lateinit var signUpLayout : ActivitySignUpBinding
    lateinit var app : readNoteApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpLayout = ActivitySignUpBinding.inflate(layoutInflater)
        app = application as readNoteApp
        setContentView(signUpLayout.root)

        signUpLayout.SignUp.setOnClickListener {
            Timber.i("SIGN UP")
            var user = UserModel()
            user.userName = signUpLayout.userName.text.toString()
            user.password = signUpLayout.password.text.toString()
            app.data.createUser(user)
            i("USER CREATED $user")
            app.data.logAllUsers()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_addbook -> {
                startActivity(Intent(this, Book::class.java))
                true
            }
            R.id.action_booklist -> {
                startActivity(Intent(this, BookList::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}