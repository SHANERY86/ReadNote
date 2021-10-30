package ie.wit.readnote.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.wit.readnote.R
import ie.wit.readnote.databinding.ActivityBookBinding
import ie.wit.readnote.main.readNoteApp
import ie.wit.readnote.models.BookModel
import org.wit.placemark.helpers.showImagePicker
import timber.log.Timber

class Book : AppCompatActivity() {
    private lateinit var binding : ActivityBookBinding
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    lateinit var app : readNoteApp
    var book = BookModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityBookBinding.inflate(layoutInflater)
        app = application as readNoteApp
        val user = app.loggedInUser
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (intent.hasExtra("bookid")) {
            val bookid = intent.getLongExtra("bookid",-1)
            book = app.data.findBookById(bookid)!!
            binding.bookTitle.setText(book.title)
            val button: Button = findViewById(R.id.addBook)
            button.setText(R.string.button_saveBook)
            val deleteButton: Button = findViewById(R.id.deleteBook)
            deleteButton.setVisibility(View.VISIBLE)
            if (book.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.button_changeCover)
            }
            Picasso.get()
                .load(book.image)
                .into(binding.bookCover)
        }

        binding.addBook.setOnClickListener() {
            Timber.i("Add Book button pressed")
            book.title = binding.bookTitle.text.toString()
            book.userId = user.id
            if(book.title.isNotEmpty()) {
                if(intent.hasExtra("bookid")) {
                    app.data.updateBook(book)
                }
                else {
                    app.data.createBook(user.id, book.copy())
                }
                setResult(RESULT_OK)
                startActivity(Intent(this, BookList::class.java))
            }
            else {
                Snackbar
                    .make(it,R.string.snackbar_EmptyBookTitle, Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)

        }

        binding.deleteBook.setOnClickListener {
            app.data.deleteBook(book)
            setResult(RESULT_OK)
            startActivity(Intent(this, BookList::class.java))
        }

        registerImagePickerCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return app.getMenuOptions(this,item)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            book.image = result.data!!.data!!
                            Picasso.get()
                                .load(book.image)
                                .into(binding.bookCover)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}