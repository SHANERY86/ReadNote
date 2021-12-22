package ie.wit.readnote.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

interface BookStore {
    fun findUserBooks(userid: String, bookList: MutableLiveData<List<BookModel>>) : List<BookModel>
    fun findBookById(userid: String, bookid: String, book: MutableLiveData<BookModel>) : BookModel?
    fun findBooks(bookList: MutableLiveData<List<BookModel>>) : List<BookModel>
    fun createBook(fireBaseUser: MutableLiveData<FirebaseUser>, book: BookModel)
    fun updateBook(userid: String, bookid: String, book: BookModel)
    fun deleteBook(userid: String, book: BookModel)
    fun createNote(book: BookModel, note: NoteModel)
    fun updateNote(book: BookModel, note: NoteModel)
    fun deleteNote(book: BookModel, note: NoteModel)
}