package ie.wit.readnote.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.readnote.models.BookModel
import ie.wit.readnote.models.FirebaseDBManager
import timber.log.Timber

class BookDetailViewModel : ViewModel() {
    private val book = MutableLiveData<BookModel>()

    var observableBook: LiveData<BookModel>
        get() = book
        set(value) {book.value = value.value}

    fun getBook(userid: String, bookid: String) {
        try {
            FirebaseDBManager.findBookById(userid,bookid,book)
        } catch (e: Exception) {
            Timber.i("Detail getBook() Error : $e.message")
        }
    }

    fun updateBook(userid: String, bookid: String, book: BookModel) {
        Timber.i("BOOK TEST: $book")
        try {
            FirebaseDBManager.updateBook(userid, bookid, book)
        }
        catch (e: Exception) {
            Timber.i("Detail update() Error : $e.message")
        }
    }
}