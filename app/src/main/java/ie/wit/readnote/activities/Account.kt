package ie.wit.readnote.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import ie.wit.readnote.R
import ie.wit.readnote.databinding.ActivityAccountBinding
import ie.wit.readnote.main.readNoteApp
import ie.wit.readnote.models.UserModel

class Account : AppCompatActivity() {
    lateinit var app : readNoteApp
    lateinit var accountLayout : ActivityAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as readNoteApp
        accountLayout = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(accountLayout.root)
        val user = app.loggedInUser
        accountLayout.userName.setText(user.userName)
        accountLayout.password.setText(user.password)
        accountLayout.Update.setOnClickListener() {
            val userNameEntry = accountLayout.userName.text.toString()
            val passwordEntry = accountLayout.password.text.toString()
            if(userNameEntry.isNotEmpty() && passwordEntry.isNotEmpty()){
                user.userName = userNameEntry
                user.password = passwordEntry
                app.data.update()
                app.imm.hideSoftInputFromWindow(it.getWindowToken(), 0)
                Snackbar
                    .make(it, R.string.snackbar_UpdatedDetails, Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        accountLayout.Delete.setOnClickListener() {
            app.data.deleteUser(user)
            app.loggedInUser = UserModel()
            setResult(RESULT_OK)
            startActivity(Intent(this, Login::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return app.getMenuOptions(this,item)
        }

}