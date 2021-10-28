package ie.wit.readnote.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import ie.wit.readnote.R
import ie.wit.readnote.databinding.ActivityLoginBinding
import ie.wit.readnote.databinding.ActivitySignUpBinding
import ie.wit.readnote.main.readNoteApp
import ie.wit.readnote.models.UserModel

class Login : AppCompatActivity() {
    private lateinit var LoginLayout : ActivityLoginBinding
    lateinit var app : readNoteApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoginLayout = ActivityLoginBinding.inflate(layoutInflater)
        app = application as readNoteApp
        setContentView(LoginLayout.root)

        LoginLayout.Login.setOnClickListener() {
            val userNameEntry = LoginLayout.userName.text.toString()
            val passwordEntry = LoginLayout.password.text.toString()
            if (userNameEntry.isNotEmpty() && passwordEntry.isNotEmpty()) {
                val users = app.data.getAllUsers()
                users.forEach { user -> if (user.userName == userNameEntry && user.password == passwordEntry) {
                    app.setUser(user)
                    setResult(RESULT_OK)
                    startActivity(Intent(this, BookList::class.java))
                }
                }
                if(app.loggedInUser.id == 0L) {
                    Snackbar
                        .make(it, R.string.snackbar_BadCreds, Snackbar.LENGTH_LONG)
                        .show()
                }
            }
            else {
                Snackbar
                    .make(it, R.string.snackbar_EmptyCreds, Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        LoginLayout.SignUp.setOnClickListener() {
            setResult(RESULT_OK)
            startActivity(Intent(this, SignUp::class.java))
        }
    }
}