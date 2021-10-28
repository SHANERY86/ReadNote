package ie.wit.readnote.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.donationx.adapters.BookAdapter
import ie.wit.donationx.adapters.BookListener
import ie.wit.readnote.R
import ie.wit.readnote.databinding.ActivityBookListBinding
import ie.wit.readnote.main.readNoteApp
import ie.wit.readnote.models.BookModel
import timber.log.Timber
import java.util.concurrent.atomic.AtomicInteger

class BookList : AppCompatActivity(), BookListener {

    var activitiesLaunched: AtomicInteger = AtomicInteger(0)

    lateinit var app : readNoteApp
    lateinit var bookListLayout : ActivityBookListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        if (activitiesLaunched.incrementAndGet() > 1) { finish(); }
        bookListLayout = ActivityBookListBinding.inflate(layoutInflater)
        setContentView(bookListLayout.root)

        app = this.application as readNoteApp
        val user = app.loggedInUser
        bookListLayout.recyclerView.layoutManager = GridLayoutManager(this,3)
        bookListLayout.recyclerView.adapter = BookAdapter(app.data.findUserBooks(user),this)
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
            R.id.action_addbook -> {
                startActivity(Intent(this, Book::class.java))
                true
            }
            R.id.action_booklist -> {
                startActivity(Intent(this, BookList::class.java))
                true
            }
            R.id.action_logout -> {
                app.logOut()
                startActivity(Intent(this, Login::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBookClick(book: BookModel) {
        super.onBookClick(book)
        val bookId : Long = book.id
        val intent: Intent
        intent = Intent(this,noteList::class.java)
        intent.putExtra("bookid",bookId)
        startActivity(intent)
    }
}