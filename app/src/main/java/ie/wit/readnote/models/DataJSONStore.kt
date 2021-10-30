package ie.wit.readnote.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import ie.wit.readnote.helpers.UriParser
import org.wit.readnote.helpers.exists
import org.wit.readnote.helpers.read
import org.wit.readnote.helpers.write
import timber.log.Timber
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
        if (exists(context, JSON_FILE))
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

    override fun createBook(userId: Long, book: BookModel) {
        book.id = generateRandomId()
        books.add(book)
        serialize()
    }

    override fun updateBook(book: BookModel) {
        val foundBook: BookModel? = books.find { b -> b.id == book.id }
        if (foundBook != null) {
            foundBook.title = book.title
            foundBook.image = book.image
        }
        serialize()
    }

    override fun deleteBook(book: BookModel) {
        val foundBook: BookModel? = books.find { b -> b.id == book.id }
        if (foundBook != null) {
            books.remove(book)
        }
        serialize()
    }

    override fun createNote(book: BookModel, note: NoteModel) {
        note.id = generateRandomId()
        i("TEST NOTE ID ${note.id}")
        i("TEST NOTE ${note}")
        val notes = book.notes
        notes.add(note)
        serialize()
    }

    override fun updateNote(book: BookModel, note: NoteModel) {
       val notes = book.notes
        val updatedNoteId = note.id
        val noteToUpdate = notes.find { n -> n.id == updatedNoteId }
        noteToUpdate?.content = note.content
        noteToUpdate?.pageNumber = note.pageNumber
        noteToUpdate?.location = if (note.location != null) note.location else null
        serialize()
    }

    override fun deleteNote(book: BookModel, note: NoteModel) {
        val notes = book.notes
        val noteToDeleteId = note.id
        val noteToDelete = notes.find { n -> n.id == noteToDeleteId }
        notes.remove(noteToDelete)
        serialize()
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

    fun logAllBooks() {
        books.forEach { Timber.i("$it")}
    }

    fun logAllUsers() {
        users.forEach { i("$it" )}
    }

    override fun getNotes(book: BookModel) : ArrayList<NoteModel> {
        return book.notes
    }

    override fun findUserById(id: Long): UserModel? {
        TODO("Not yet implemented")
    }

    override fun createUser(user: UserModel) {
        user.id = generateRandomId()
        i("TEST USER $user")
        users.add(user)
        serialize()
    }

    override fun updateUser(user: UserModel) {
        TODO("Not yet implemented")
    }

    override fun deleteUser(user: UserModel) {
        TODO("Not yet implemented")
    }

    override fun getAllUsers() : MutableList<UserModel> {
        return users
    }

}


