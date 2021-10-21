package ie.wit.readnote.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.donationx.adapters.NoteAdapter
import ie.wit.readnote.R
import ie.wit.readnote.databinding.ActivityNoteListBinding
import ie.wit.readnote.main.readNoteApp
import java.util.concurrent.atomic.AtomicInteger

class noteList : AppCompatActivity() {

    var activitiesLaunched: AtomicInteger = AtomicInteger(0)

    lateinit var app : readNoteApp
    lateinit var noteListLayout : ActivityNoteListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        if (activitiesLaunched.incrementAndGet() > 1) { finish(); }
        noteListLayout = ActivityNoteListBinding.inflate(layoutInflater)
        setContentView(noteListLayout.root)

        app = this.application as readNoteApp
        noteListLayout.recyclerView.layoutManager = LinearLayoutManager(this)
        noteListLayout.recyclerView.adapter = NoteAdapter(app.notes.findAll())
        super.onCreate(savedInstanceState)
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
            R.id.action_note -> {
                startActivity(Intent(this, Note::class.java))
                true
            }
            R.id.action_addbook -> {
                startActivity(Intent(this, Book::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}