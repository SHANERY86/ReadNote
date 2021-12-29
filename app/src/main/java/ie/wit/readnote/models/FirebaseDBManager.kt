package ie.wit.readnote.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import timber.log.Timber

object FirebaseDBManager : BookStore {
    var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun findUserBooks(userid: String, bookList: MutableLiveData<List<BookModel>>) {
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
    ) {
        database.child("user-books").child(userid)
            .child(bookid).get().addOnSuccessListener {
                book.value = it.getValue(BookModel::class.java)
                Timber.i("firebase Got value ${it.value}")
            }.addOnFailureListener{
                Timber.e("firebase Error getting data $it")
            }
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
        val bookValues = book.toMap()
        val childUpdate : MutableMap<String, Any?> = HashMap()
        childUpdate["books/$bookid"] = bookValues
        childUpdate["user-books/$userid/$bookid"] = bookValues

        database.updateChildren(childUpdate)
    }

    override fun deleteBook(userid: String, bookid: String) {
        val childDelete : MutableMap<String, Any?> = HashMap()
        childDelete["/books/$bookid"] = null
        childDelete["/user-books/$userid/$bookid"] = null
        childDelete["/notes/$bookid"] = null
        childDelete["/user-notes/$userid/$bookid"] = null

        database.updateChildren(childDelete)
    }

    override fun createNote(userid: String, bookid: String, note: NoteModel) {
        val key = database.child("notes").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        note.uid = key
        val noteValues = note.toMap()
        val childAdd = HashMap<String, Any>()
        childAdd["/notes/$bookid/$key"] = noteValues
        childAdd["/user-notes/$userid/$bookid/$key"] = noteValues

        database.updateChildren(childAdd)
    }

    override fun getBookNotes(userid: String, bookid: String, notes: MutableLiveData<ArrayList<NoteModel>>) {

                database.child("user-notes").child(userid).child(bookid)
                .addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        Timber.i("Firebase readNote error : ${error.message}")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val localList = ArrayList<NoteModel>()
                        val children = snapshot.children
                        children.forEach {
                            val note = it.getValue(NoteModel::class.java)
                            localList.add(note!!)
                        }
                        database.child("user-books").child(userid)
                            .removeEventListener(this)

                        notes.value = localList
                    }
                })
    }

    override fun updateNote(book: BookModel, note: NoteModel) {
        TODO("Not yet implemented")
    }

    override fun deleteNote(userid: String, bookid: String, noteid: String) {
        val childDelete : MutableMap<String, Any?> = HashMap()
        childDelete["/notes/$bookid/$noteid"] = null
        childDelete["/user-notes/$userid/$bookid/$noteid"] = null

        database.updateChildren(childDelete)
    }


}