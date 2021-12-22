package ie.wit.readnote.ui.book

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.readnote.models.BookModel
import ie.wit.readnote.models.FirebaseDBManager
import timber.log.Timber

class BookViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()
    private val book = MutableLiveData<BookModel>()

    val observableStatus: LiveData<Boolean>
        get() = status

    var observableBook: LiveData<BookModel>
        get() = book
        set(value) {book.value = value.value}

    fun addBook(firebaseUser: MutableLiveData<FirebaseUser>, book: BookModel) {
        status.value = try {
            FirebaseDBManager.createBook(firebaseUser, book)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    fun seeBook(bookid: Long) {
//        book.value = FirebaseDBManager.findBookById(bookid)
    }

    fun updateBook(bookid: Long, book: BookModel) {
        Timber.i("BOOK TEST: $book")
//            FirebaseDBManager.updateBook(bookid, book)
        }

/*    fun findBook(bookid: Long) : BookModel? {
        val book = FirebaseDBManager.findBookById(bookid)
        if (book != null) return book
        else return null
    } */
}