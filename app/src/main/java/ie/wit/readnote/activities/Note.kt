package ie.wit.readnote.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
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

        if(intent.hasExtra("note_edit")){
            note = intent.extras?.getParcelable("note_edit")!!
            binding.addContent.setText(note.content)
            val button: Button = findViewById(R.id.addNote)
            button.setText(R.string.button_saveNote)
            val deleteButton: Button = findViewById(R.id.deleteNote)
            deleteButton.setVisibility(View.VISIBLE)
        }

        binding.addNote.setOnClickListener() {
            note.content = binding.addContent.text.toString()
            val pageNo = binding.addPageNumber.text.toString()
            if(pageNo.isNotEmpty()){
                val pageNoInt = pageNo.toInt()
                note.pageNumber = applicationContext.getString(R.string.pageNo_OnScreen,pageNoInt)
            }
            if (note.content.isNotEmpty()) {
                if(intent.hasExtra("note_edit")) {
                    app.books.updateNote(book, note)
                }
                else {
                    app.books.createNote(book, note.copy())
                }
                startNoteList()
            } else {
                Snackbar
                    .make(it, R.string.snackbar_EmptyNoteContent, Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        binding.deleteNote.setOnClickListener() {
            Timber.i("Delete button pushed")
            app.books.deleteNote(book, note)
            startNoteList()
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


    fun startNoteList(){
        setResult(RESULT_OK)
        intent = Intent(this,noteList::class.java)
        intent.putExtra("bookid",bookId)
        startActivity(intent)
    }


}

