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

    val observableBook: LiveData<BookModel>
        get() = book

    fun addBook(book: BookModel) {
        status.value = try {
            DataManager.createBook(book)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    fun getBook(bookid: Long) {
        book.value = DataManager.findBookById(bookid)
    }

    fun updateBook(book: BookModel) {
            DataManager.updateBook(book)
            DataManager.logAll()
        }
}