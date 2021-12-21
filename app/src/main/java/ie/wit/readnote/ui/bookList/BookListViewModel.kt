package ie.wit.readnote.ui.bookList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.readnote.models.BookModel
import ie.wit.readnote.models.DataManager

class BookListViewModel : ViewModel() {

    private val bookList = MutableLiveData<List<BookModel>>()

    val observableBookList: LiveData<List<BookModel>>
        get() = bookList

    init {
        load()
    }

    fun load() {
        bookList.value = DataManager.findBooks()
    }
}