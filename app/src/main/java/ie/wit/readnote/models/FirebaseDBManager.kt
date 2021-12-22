package ie.wit.readnote.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import timber.log.Timber

object FirebaseDBManager : BookStore {
    var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun findUserBooks(userid: String, bookList: MutableLiveData<List<BookModel>>) {
                Timber.i("TEST USER ID $userid")
                database.child("user-books").child(userid)
                .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase readNote error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<BookModel>()
                    val children = snapshot.children
                    children.forEach {
                        val book = it.getValue(BookModel::class.java)
                        localList.add(book!!)
                    }
                    database.child("user-books").child(userid)
                        .removeEventListener(this)

                    bookList.value = localList
                }
            })
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

    override fun createBook(firebaseUser: MutableLiveData<FirebaseUser>, book: BookModel) {
        Timber.i("Firebase DB Reference : $database")

        val uid = firebaseUser.value!!.uid
        val key = database.child("books").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        book.uid = key
        val bookValues = book.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/books/$key"] = bookValues
        childAdd["/user-books/$uid/$key"] = bookValues

        database.updateChildren(childAdd)
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