package ie.wit.readnote.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
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
            i("SIGN UP")
            var user = UserModel()
            var userFound = false
            val userNameEntry = signUpLayout.userName.text.toString()
            user.userName = signUpLayout.userName.text.toString()
            user.password = signUpLayout.password.text.toString()
            val users = app.data.getAllUsers()
            users.forEach { if(user.userName == userNameEntry) {
                userFound = true
            } }
            if(!userFound) {
                app.data.createUser(user)
                app.loggedInUser = user
                i("USER CREATED $user")
                app.setUser(user)
                i("LOGGED IN USER ${app.loggedInUser}")
                app.setUser(user)
                setResult(RESULT_OK)
                startActivity(Intent(this, BookList::class.java))
            }
            else {
                Snackbar
                    .make(it, R.string.snackbar_UsernameTaken, Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

}