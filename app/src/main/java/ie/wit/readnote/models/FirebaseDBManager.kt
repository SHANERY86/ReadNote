package ie.wit.readnote.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

object FirebaseDBManager : BookStore {
    override fun findUserBooks(
        userid: String,
        bookList: MutableLiveData<List<BookModel>>
    ): List<BookModel> {
        TODO("Not yet implemented")
    }

    override fun findBookById(
        userid: String,
        bookid: String,
        book: MutableLiveData<BookModel>
    ): BookModel? {
        TODO("Not yet implemented")
    }

    override fun findBooks(bookList: MutableLiveData<List<BookModel>>): List<BookModel> {
        TODO("Not yet implemented")
    }

    override fun createBook(fireBaseUser: MutableLiveData<FirebaseUser>, book: BookModel) {
        TODO("Not yet implemented")
    }

    override fun updateBook(userid: String, bookid: String, book: BookModel) {
        TODO("Not yet implemented")
    }

    override fun deleteBook(userid: String, book: BookModel) {
        TODO("Not yet implemented")
    }

    override fun createNote(book: BookModel, note: NoteModel) {
        TODO("Not yet implemented")
    }

    override fun updateNote(book: BookModel, note: NoteModel) {
        TODO("Not yet implemented")
    }

    override fun deleteNote(book: BookModel, note: NoteModel) {
        TODO("Not yet implemented")
    }

}