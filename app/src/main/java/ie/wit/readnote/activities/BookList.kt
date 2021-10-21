package ie.wit.readnote.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.donationx.adapters.BookAdapter
import ie.wit.readnote.R
import ie.wit.readnote.databinding.ActivityBookListBinding
import ie.wit.readnote.main.readNoteApp
import java.util.concurrent.atomic.AtomicInteger

class BookList : AppCompatActivity() {

    var activitiesLaunched: AtomicInteger = AtomicInteger(0)

    lateinit var app : readNoteApp
    lateinit var bookListLayout : ActivityBookListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        if (activitiesLaunched.incrementAndGet() > 1) { finish(); }
        bookListLayout = ActivityBookListBinding.inflate(layoutInflater)
        setContentView(bookListLayout.root)

        app = this.application as readNoteApp
        bookListLayout.recyclerView.layoutManager = LinearLayoutManager(this)
        bookListLayout.recyclerView.adapter = BookAdapter(app.books.findAll())
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