package ie.wit.readnote.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import ie.wit.readnote.R
import ie.wit.readnote.databinding.ActivityAccountBinding
import ie.wit.readnote.main.readNoteApp

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
            }
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
        return app.getMenuOptions(this,item)
        }

}