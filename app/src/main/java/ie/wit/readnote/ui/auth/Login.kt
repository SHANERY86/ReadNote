package ie.wit.readnote.ui.auth

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import ie.wit.readnote.R
import ie.wit.readnote.databinding.ActivityLoginBinding
import ie.wit.readnote.main.readNoteApp
import timber.log.Timber


class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var LoginLayout : ActivityLoginBinding
    lateinit var app : readNoteApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoginLayout = ActivityLoginBinding.inflate(layoutInflater)
        app = application as readNoteApp
        setContentView(LoginLayout.root)
        auth = FirebaseAuth.getInstance()

        LoginLayout.Login.setOnClickListener() {

            val userNameEntry = LoginLayout.email.text.toString()
            val passwordEntry = LoginLayout.password.text.toString()
            if (userNameEntry.isNotEmpty() && passwordEntry.isNotEmpty()) {
/*                val users = app.data.users
                i("TEST USERS: $users")
                users.forEach { user -> if (user.userName == userNameEntry && user.password == passwordEntry) {
                    app.setUser(user)
                    setResult(RESULT_OK)
      //              startActivity(Intent(this, BookList::class.java))
                }
                } */
                if(app.loggedInUser.id == 0L) {
                    app.imm.hideSoftInputFromWindow(it.getWindowToken(), 0)
                    Snackbar
                        .make(it, R.string.snackbar_BadCreds, Snackbar.LENGTH_LONG)
                        .show()
                }
            }
            else {
                app.imm.hideSoftInputFromWindow(it.getWindowToken(), 0)
                Snackbar
                    .make(it, R.string.snackbar_EmptyCreds, Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        LoginLayout.SignUp.setOnClickListener() {
            auth.createUserWithEmailAndPassword(LoginLayout.email.text.toString(), LoginLayout.password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Timber.d( "createUserWithEmail:success")
                        val user = auth.currentUser
                        findNavController(R.id.nav_host_fragment).navigate(R.id.bookListFragment)
                    } else {
                        // If sign in fails, display a message to the user.
                        Timber.w( "createUserWithEmail:failure $task.exception")
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
//                        updateUI(null)
                    }

                    // [START_EXCLUDE]
//                    hideLoader(loader)
                    // [END_EXCLUDE]
                }
        }
    }


}