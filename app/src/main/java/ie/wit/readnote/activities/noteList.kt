package ie.wit.readnote.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.donationx.adapters.NoteAdapter
import ie.wit.donationx.adapters.NoteListener
import ie.wit.readnote.R
import ie.wit.readnote.databinding.ActivityNoteListBinding
import ie.wit.readnote.main.readNoteApp
import ie.wit.readnote.models.BookModel
import ie.wit.readnote.models.NoteModel
import timber.log.Timber
import timber.log.Timber.i
import java.util.concurrent.atomic.AtomicInteger

class noteList : AppCompatActivity(), NoteListener {
    var activitiesLaunched: AtomicInteger = AtomicInteger(0)
    lateinit var app : readNoteApp
    lateinit var noteListLayout : ActivityNoteListBinding
    var bookId : Long = 0L
    var book = BookModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        if (activitiesLaunched.incrementAndGet() > 1) { finish(); }
        noteListLayout = ActivityNoteListBinding.inflate(layoutInflater)
        setContentView(noteListLayout.root)
        app = this.application as readNoteApp
        bookId = intent.getLongExtra("bookid",-1)
        book = app.data.findBookById(bookId)!!
        noteListLayout.recyclerView.layoutManager = LinearLayoutManager(this)
        noteListLayout.recyclerView.adapter = NoteAdapter(book.notes, this)
        super.onCreate(savedInstanceState)

        noteListLayout.newNote.setOnClickListener {
            intent = Intent(this,Note::class.java)
            intent.putExtra("bookid",bookId)
            startActivity(intent)
        }

        noteListLayout.editBook.setOnClickListener {
//            intent = Intent(this,Book::class.java)
            intent.putExtra("bookid",bookId)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

 /*   override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return app.getMenuOptions(this,item)
    } */

    override fun onNoteClick(note: NoteModel) {
        super.onNoteClick(note)
        val noteToEdit : NoteModel = note
        val intent: Intent
        intent = Intent(this,Note::class.java)
        intent.putExtra("note_edit",noteToEdit)
        intent.putExtra("bookid",book.id)
        startActivity(intent)
    }
}