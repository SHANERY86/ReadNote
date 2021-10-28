package ie.wit.readnote.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.readnote.helpers.exists
import org.wit.readnote.helpers.read
import org.wit.readnote.helpers.write
import timber.log.Timber
import timber.log.Timber.i
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

const val JSON_FILE = "data.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val bookListType: Type = object : TypeToken<ArrayList<BookModel>>() {}.type
val userListType: Type = object : TypeToken<ArrayList<UserModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class DataJSONStore(private val context: Context) : BookStore, UserStore {

    var books = mutableListOf<BookModel>()
    var users = mutableListOf<UserModel>()

    init {
        if (exists(context, JSON_FILE))
            deserialize()
    }

    override fun findAllBooks(): List<BookModel> {
        logAllBooks()
        return books
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
        val notes = book.notes
        notes.add(note)
        serialize()
    }

    override fun updateNote(book: BookModel, note: NoteModel) {
        val notes = book.notes
        val updatedNoteId = note.id
        val noteToUpdate = notes.find { n -> n.id == updatedNoteId }
        noteToUpdate?.content = note.content
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
        books = gsonBuilder.fromJson(jsonString, bookListType)
        users = gsonBuilder.fromJson(jsonString,userListType)
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
        users.add(user)
        serialize()
    }

    override fun updateUser(user: UserModel) {
        TODO("Not yet implemented")
    }

    override fun deleteUser(user: UserModel) {
        TODO("Not yet implemented")
    }

}


class UriParser : JsonDeserializer<Uri>, JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }
    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}