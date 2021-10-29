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
        book = app.data.findBookById(bookId)!!
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.pagePicker.maxValue = 1000
        binding.pagePicker.minValue = 1
        binding.pagePicker.setOnValueChangedListener { _, _, newVal ->
            binding.addPageNumber.setText("$newVal")
        }

        if(intent.hasExtra("note_edit")){
            note = intent.extras?.getParcelable("note_edit")!!
            binding.addContent.setText(note.content)
            val pageNumber = note.pageNumber.replace("page ","")
            binding.addPageNumber.setText(pageNumber)
            binding.pagePicker.setValue(pageNumber.toInt())
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
                    app.data.updateNote(book, note)
                }
                else {
                    app.data.createNote(book, note.copy())
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
            app.data.deleteNote(book, note)
            startNoteList()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return app.getMenuOptions(this,item)
        }


    fun startNoteList(){
        setResult(RESULT_OK)
        intent = Intent(this,noteList::class.java)
        intent.putExtra("bookid",bookId)
        startActivity(intent)
    }


}

