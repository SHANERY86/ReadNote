package ie.wit.readnote.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import ie.wit.readnote.helpers.UriParser
import org.wit.readnote.helpers.exists
import org.wit.readnote.helpers.read
import org.wit.readnote.helpers.write
import timber.log.Timber.i
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList


val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val bookListType: Type = object : TypeToken<ArrayList<BookModel>>() {}.type
val userListType: Type = object : TypeToken<ArrayList<UserModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class DataJSONStore(private val context: Context, val JSON_FILE : String) : BookStore, UserStore {
    var books = mutableListOf<BookModel>()
    var users = mutableListOf<UserModel>()

    init {
        if (exists(context, JSON_FILE) || (JSON_FILE.contains("test")))
            deserialize()
    }

    override fun findUserBooks(user: UserModel): List<BookModel> {
        var foundBooks = mutableListOf<BookModel>()
        books.forEach { book -> if (user.id == book.userId) {
            foundBooks.add(book)
        }
        }
        return foundBooks
    }

    override fun findBookById(id: Long): BookModel? {
        val foundBook = books.find { b -> b.id == id }
        return foundBook
    }

    override fun findBooks(): List<BookModel> {
        return books
    }

    override fun createBook(book: BookModel) {
//        book.id = generateRandomId()
        books.add(book)
        update()
    }

    override fun updateBook(bookid: Long, book: BookModel) {
        val foundBook: BookModel? = books.find { b -> b.id == bookid }
        if (foundBook != null) {
            foundBook.title = book.title
            foundBook.image = book.image
        }
        update()
    }

    override fun deleteBook(book: BookModel) {
        val foundBook: BookModel? = books.find { b -> b.id == book.id }
        if (foundBook != null) {
            books.remove(book)
        }
        update()
    }

    override fun createNote(book: BookModel, note: NoteModel) {
        note.id = generateRandomId()
        val notes = book.notes
        notes.add(note)
        update()
    }

    override fun updateNote(book: BookModel, note: NoteModel) {
       val notes = book.notes
        val updatedNoteId = note.id
        val noteToUpdate = notes.find { n -> n.id == updatedNoteId }
        noteToUpdate?.content = note.content
        noteToUpdate?.pageNumber = note.pageNumber
        noteToUpdate?.location = if (note.location != null) note.location else null
        update()
    }

    override fun deleteNote(book: BookModel, note: NoteModel) {
        val notes = book.notes
        val noteToDeleteId = note.id
        val noteToDelete = notes.find { n -> n.id == noteToDeleteId }
        notes.remove(noteToDelete)
        update()
    }

    private fun serialize() {
        val bookJsonString = if (books.isNotEmpty()) "\"books\": " + gsonBuilder.toJson(books, bookListType) + ", " else ""
        val usersJsonString = if (users.isNotEmpty()) "\"users\": " + gsonBuilder.toJson(users, userListType) else ""
        val totalJsonString = "{ $bookJsonString$usersJsonString }"
        write(context, JSON_FILE, totalJsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        val jsonData : JsonObject = gsonBuilder.fromJson(jsonString, JsonObject::class.java)
        val booksArray = jsonData.getAsJsonArray("books")
        val usersArray = jsonData.getAsJsonArray("users")
        if (booksArray != null) {
            val booksString = booksArray.toString()
            books = gsonBuilder.fromJson(booksString, bookListType)
        }
        if (usersArray != null) {
            val usersString = usersArray.toString()
            users = gsonBuilder.fromJson(usersString, userListType)
        }
    }


    override fun createUser(user: UserModel) {
        user.id = generateRandomId()
        i("TEST USER $user")
        users.add(user)
        update()
    }

    override fun deleteUser(user: UserModel) {
        books.forEach { book ->
            if (book.userId == user.id) {
                books.remove(book)
            }
        }
        users.remove(user)
        update()
    }

    fun update() {
        if(!JSON_FILE.equals("test")) {
            serialize()
        }
    }

}


