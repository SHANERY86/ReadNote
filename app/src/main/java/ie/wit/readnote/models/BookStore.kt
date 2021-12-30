package ie.wit.readnote.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

interface BookStore {
    fun findUserBooks(userid: String, bookList: MutableLiveData<List<BookModel>>)
    fun findBookById(userid: String, bookid: String, book: MutableLiveData<BookModel>)
    fun createBook(fireBaseUser: MutableLiveData<FirebaseUser>, book: BookModel)
    fun updateBook(userid: String, bookid: String, book: BookModel)
    fun deleteBook(userid: String, bookid: String)
    fun createNote(userid: String, bookid: String, note: NoteModel)
    fun updateNote(userid: String, bookid: String, noteid: String, note: NoteModel)
    fun deleteNote(userid: String, bookid: String, noteid: String)
    fun getBookNotes(userid: String, bookid: String, notes: MutableLiveData<ArrayList<NoteModel>>)
    fun findNoteById(userid: String, bookid: String, noteid: String, note: MutableLiveData<NoteModel>)
    fun makeNoteImportant(userid: String, bookid: String, noteid: String, note: NoteModel)
    fun getImportantNotes(userid: String, bookid: String, notes: MutableLiveData<ArrayList<NoteModel>>)
}