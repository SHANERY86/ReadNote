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

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

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

    fun getBook(userid: String, bookid: String) {
        FirebaseDBManager.findBookById(userid,bookid,book)
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

    fun changeImageURI(uri: String) {

    }

/*    fun findBook(bookid: Long) : BookModel? {
        val book = FirebaseDBManager.findBookById(bookid)
        if (book != null) return book
        else return null
    } */
}