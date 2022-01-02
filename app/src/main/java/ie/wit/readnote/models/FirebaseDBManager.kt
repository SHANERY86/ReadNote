package ie.wit.readnote.models

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import ie.wit.readnote.firebase.FirebaseImageManager
import ie.wit.readnote.main.readNoteApp
import ie.wit.readnote.ui.book.BookFragmentArgs
import org.wit.placemark.helpers.readImageFromPath
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File


object FirebaseDBManager : BookStore {
    var database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private lateinit var firebaseImageManager: FirebaseImageManager

    override fun findUserBooks(userid: String, bookList: MutableLiveData<ArrayList<BookModel>>) {
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

    override fun createBook(firebaseUser: MutableLiveData<FirebaseUser>, book: BookModel) {
        firebaseImageManager = FirebaseImageManager()
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
        childAdd["/user-books/$uid/$key"] = bookValues

        database.updateChildren(childAdd)
        firebaseImageManager.storeCoverImage(firebaseUser.value!!.uid,book)
    }

    override fun updateBook(userid: String, bookid: String, book: BookModel) {
        val bookValues = book.toMap()
        val childUpdate : MutableMap<String, Any?> = HashMap()
        childUpdate["user-books/$userid/$bookid"] = bookValues

        database.updateChildren(childUpdate)
    }

    override fun deleteBook(userid: String, bookid: String) {
        val childDelete : MutableMap<String, Any?> = HashMap()
        childDelete["/user-books/$userid/$bookid"] = null
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
                        database.child("user-notes").child(userid).child(bookid)
                            .removeEventListener(this)

                        notes.value = localList
                    }
                })
    }

    override fun findNoteById(userid: String, bookid: String, noteid: String, note: MutableLiveData<NoteModel>) {
        database.child("user-notes").child(userid).child(bookid).child(noteid)
            .get().addOnSuccessListener {
                note.value = it.getValue(NoteModel::class.java)
                Timber.i("firebase Got value ${it.value}")
            }.addOnFailureListener{
                Timber.e("firebase Error getting data $it")
            }
    }

    override fun updateNote(userid: String, bookid: String, noteid: String, note: NoteModel) {
        val noteValues = note.toMap()
        val childUpdate : MutableMap<String, Any?> = HashMap()
        childUpdate["user-notes/$userid/$bookid/$noteid"] = noteValues

        database.updateChildren(childUpdate)
    }

    override fun deleteNote(userid: String, bookid: String, noteid: String) {
        val childDelete : MutableMap<String, Any?> = HashMap()
        childDelete["/user-notes/$userid/$bookid/$noteid"] = null

        database.updateChildren(childDelete)
    }

    override fun getImportantNotes(
        userid: String,
        bookid: String,
        notes: MutableLiveData<ArrayList<NoteModel>>
    ) {
        database.child("user-notes").child(userid).child(bookid).orderByChild("nb").equalTo(true)
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
                    database.child("user-notes").child(userid).child(bookid).orderByChild("nb").equalTo(true)
                        .removeEventListener(this)

                    notes.value = localList
                }
            })
    }

    override fun getFavouriteBooks(
        userid: String,
        books: MutableLiveData<ArrayList<BookModel>>
    ) {
        database.child("user-books").child(userid).orderByChild("fav").equalTo(true)
            .addValueEventListener(object: ValueEventListener {
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
                    database.child("user-books").child(userid).orderByChild("fav").equalTo(true)
                        .removeEventListener(this)

                    books.value = localList
                }
            })
    }



}