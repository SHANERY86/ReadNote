package ie.wit.readnote.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ie.wit.readnote.R
import ie.wit.readnote.databinding.ActivityLoginBinding
import ie.wit.readnote.databinding.ActivitySignUpBinding
import ie.wit.readnote.main.readNoteApp
import ie.wit.readnote.models.UserModel

class Login : AppCompatActivity() {
    private lateinit var LoginLayout : ActivityLoginBinding
    lateinit var app : readNoteApp
    var user = UserModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoginLayout = ActivityLoginBinding.inflate(layoutInflater)
        app = application as readNoteApp
        setContentView(LoginLayout.root)

        LoginLayout.Login.setOnClickListener() {
            val userNameEntry = LoginLayout.userName.text.toString()
            val passwordEntry = LoginLayout.password.text.toString()
        }
    }
}