package ie.wit.readnote.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.readnote.models.BookModel
import ie.wit.readnote.models.DataManager
import timber.log.Timber

class BookViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()
    private val book = MutableLiveData<BookModel>()

    val observableStatus: LiveData<Boolean>
        get() = status

    var observableBook: LiveData<BookModel>
        get() = book
        set(value) {book.value = value.value}

    fun addBook(book: BookModel) {
        status.value = try {
            DataManager.createBook(book)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    fun seeBook(bookid: Long) {
        book.value = DataManager.findBookById(bookid)
    }

    fun updateBook(bookid: Long, book: BookModel) {
        Timber.i("BOOK TEST: $book")
            DataManager.updateBook(bookid, book)
            DataManager.logAll()
        }

    fun findBook(bookid: Long) : BookModel? {
        val book = DataManager.findBookById(bookid)
        if (book != null) return book
        else return null
    }
}