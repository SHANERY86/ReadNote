package ie.wit.readnote.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.readnote.models.BookModel
import ie.wit.readnote.models.DataManager

class BookViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addBook(book: BookModel) {
        status.value = try {
            DataManager.createBook(book)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}