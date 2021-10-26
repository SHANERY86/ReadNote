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

const val BOOKS_JSON_FILE = "books.json"
val BookGsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, BookUriParser())
    .create()
val BookListType: Type = object : TypeToken<ArrayList<BookModel>>() {}.type

fun bookGenerateRandomId(): Long {
    return Random().nextLong()
}

class BookJSONStore(private val context: Context) : BookStore {

    var books = mutableListOf<BookModel>()

    init {
        if (exists(context, BOOKS_JSON_FILE))
            deserialize()
    }

    override fun findAll(): List<BookModel> {
        logAll()
        return books
    }

    override fun findById(id: Long): BookModel? {
        val foundBook = books.find { b -> b.id == id }
        return foundBook
    }

    override fun create(book: BookModel) {
        book.id = bookGenerateRandomId()
        books.add(book)
        serialize()
    }

    override fun update(book: BookModel) {
        val foundBook: BookModel? = books.find { b -> b.id == book.id }
        if (foundBook != null) {
            foundBook.title = book.title
            foundBook.image = book.image
        }
        serialize()
    }

    override fun delete(book: BookModel) {
        val foundBook: BookModel? = books.find { b -> b.id == book.id }
        if (foundBook != null) {
            books.remove(book)
        }
        serialize()
    }

    override fun createNote(book: BookModel, note: NoteModel) {
        note.id = bookGenerateRandomId()
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
        i("TEST NOTE DELETE: ${noteToDelete}")
        notes.remove(noteToDelete)
        serialize()
    }

    private fun serialize() {
        val jsonString = BookGsonBuilder.toJson(books, BookListType)
        write(context, BOOKS_JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, BOOKS_JSON_FILE)
        books = BookGsonBuilder.fromJson(jsonString, BookListType)
    }

    private fun logAll() {
        books.forEach { Timber.i("$it")}
    }

    override fun getNotes(book: BookModel) : ArrayList<NoteModel> {
        return book.notes
    }

}


class BookUriParser : JsonDeserializer<Uri>, JsonSerializer<Uri> {
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