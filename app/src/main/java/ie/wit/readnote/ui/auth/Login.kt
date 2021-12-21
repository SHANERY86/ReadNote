package ie.wit.readnote.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import ie.wit.readnote.R
import ie.wit.readnote.activities.Home
import ie.wit.readnote.databinding.ActivityLoginBinding
import ie.wit.readnote.main.readNoteApp
import timber.log.Timber


class Login : AppCompatActivity() {

    private lateinit var loginViewModel : LoginViewModel
    private lateinit var LoginLayout : ActivityLoginBinding
    lateinit var app : readNoteApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoginLayout = ActivityLoginBinding.inflate(layoutInflater)
        app = application as readNoteApp
        setContentView(LoginLayout.root)


        LoginLayout.Login.setOnClickListener() {
            signIn(LoginLayout.email.text.toString(),LoginLayout.password.text.toString())
        }

        LoginLayout.SignUp.setOnClickListener() {
            createAccount(LoginLayout.email.text.toString(),LoginLayout.password.text.toString())
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        loginViewModel.liveFirebaseUser.observe(this, Observer
        { firebaseUser -> if (firebaseUser != null)
            startActivity(Intent(this, Home::class.java)) })

        loginViewModel.firebaseAuthManager.errorStatus.observe(this, Observer
        { status -> checkStatus(status) })
    }

    private fun createAccount(email: String, password: String) {
        Timber.d("createAccount:$email")
        if (!validateForm()) { return }

        loginViewModel.register(email,password)
    }

    private fun signIn(email: String, password: String) {
        Timber.d("signIn:$email")
        if (!validateForm()) { return }

        loginViewModel.login(email,password)
    }

    private fun checkStatus(error:Boolean) {
        if (error)
            Toast.makeText(this,
                getString(R.string.auth_failed),
                Toast.LENGTH_LONG).show()
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = LoginLayout.email.text.toString()
        if (TextUtils.isEmpty(email)) {
            LoginLayout.email.error = "Required."
            valid = false
        } else {
            LoginLayout.email.error = null
        }

        val password = LoginLayout.password.text.toString()
        if (TextUtils.isEmpty(password)) {
            LoginLayout.password.error = "Required."
            valid = false
        } else {
            LoginLayout.password.error = null
        }
        return valid
    }

}