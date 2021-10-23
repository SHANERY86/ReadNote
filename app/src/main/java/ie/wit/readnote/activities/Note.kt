package ie.wit.readnote.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import com.google.android.material.snackbar.Snackbar
import ie.wit.readnote.R
import ie.wit.readnote.databinding.ActivityMainBinding
import ie.wit.readnote.main.readNoteApp
import ie.wit.readnote.models.BookModel
import ie.wit.readnote.models.NoteModel
import timber.log.Timber
import java.util.concurrent.atomic.AtomicInteger


class Note : AppCompatActivity() {
    var activitiesLaunched: AtomicInteger = AtomicInteger(0)
    private lateinit var binding : ActivityMainBinding
    lateinit var app : readNoteApp
    var note = NoteModel()
    var bookId : Long = 0L
    var book = BookModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        if (activitiesLaunched.incrementAndGet() > 1) { finish(); }
        binding = ActivityMainBinding.inflate(layoutInflater)
        app = application as readNoteApp
        bookId = intent.getLongExtra("bookid",-1)
        book = app.books.findById(bookId)!!
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.addNote.setOnClickListener() {
            Timber.i("Add Note button pressed for " + book.title)
            note.content = binding.addContent.text.toString()
            if (note.content.isNotEmpty()) {
                Timber.i("TEST ${note}")
                app.notes.create(book,note.copy())
                app.books.logAll()
                setResult(RESULT_OK)
                intent = Intent(this,noteList::class.java)
                intent.putExtra("bookid",bookId)
                startActivity(intent)
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

