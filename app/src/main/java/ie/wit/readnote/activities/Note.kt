package ie.wit.readnote.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import ie.wit.readnote.R
import ie.wit.readnote.databinding.ActivityMainBinding
import ie.wit.readnote.main.readNoteApp
import ie.wit.readnote.models.NoteModel
import timber.log.Timber


class Note : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    lateinit var app : readNoteApp
    var note = NoteModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        app = application as readNoteApp
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.addNote.setOnClickListener() {
            Timber.i("Add Note button pressed")
            note.content = binding.addContent.text.toString()
            if (note.content.isNotEmpty()) {
                app.notes.create(note.copy())
                setResult(RESULT_OK)
                startActivity(Intent(this, noteList::class.java))
            } else {
                Snackbar
                    .make(it, R.string.snackbar_EmptyNoteContent, Snackbar.LENGTH_LONG)
                    .show()
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
        return when (item.itemId) {
            R.id.action_note -> {
                startActivity(Intent(this, Note::class.java))
                true
            }
            R.id.action_addbook -> {
                startActivity(Intent(this, Book::class.java))
                true
            }
            R.id.action_notelist -> {
                startActivity(Intent(this, noteList::class.java))
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