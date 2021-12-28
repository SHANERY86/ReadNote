package ie.wit.readnote.ui.bookList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseUser
import ie.wit.donationx.ui.auth.LoggedInViewModel
import ie.wit.readnote.models.BookModel
import ie.wit.readnote.models.FirebaseDBManager
import timber.log.Timber


class BookListViewModel : ViewModel() {

    private val bookList = MutableLiveData<List<BookModel>>()

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    val observableBookList: LiveData<List<BookModel>>
        get() = bookList

    init {
        load()
    }

    fun load() {

        try {
            Timber.i("TEST: ${liveFirebaseUser.value?.uid!!}" )
            FirebaseDBManager.findUserBooks(liveFirebaseUser.value?.uid!!, bookList)
        }
        catch (e: Exception) {
            Timber.i("Book List Load Error : ${e.message}")
        }
    }
}