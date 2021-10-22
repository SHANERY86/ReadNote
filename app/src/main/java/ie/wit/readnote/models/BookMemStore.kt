package ie.wit.readnote.models

import timber.log.Timber

var lastBookId = 0L

internal fun getBookId(): Long {
    return lastBookId++
}

class BookMemStore : BookStore {

    val books = ArrayList<BookModel>()

    override fun findAll(): List<BookModel> {
        return books
    }

    override fun findById(id:Long) : BookModel? {
        val foundBook: BookModel? = books.find { it.id == id }
        return foundBook
    }

    override fun create(book: BookModel) {
        book.id = getId()
        books.add(book)
        logAll()
    }

    override fun addNote(notes: ArrayList<NoteModel>, note: NoteModel) {
        notes.add(note)
    }

    fun getNotes(book: BookModel) : ArrayList<NoteModel> {
        return book.notes
    }

    fun logAll() {
        Timber.v("** Books List **")
        books.forEach { Timber.v("Book ${it}") }
    }

}