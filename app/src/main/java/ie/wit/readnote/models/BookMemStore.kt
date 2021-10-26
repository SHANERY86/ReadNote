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
        book.id = getBookId()
        books.add(book)
        logAll()
    }

    override fun update(book: BookModel){
        val foundBook: BookModel? = books.find { b -> b.id == book.id }
        if (foundBook != null) {
            foundBook.title = book.title
            foundBook.image = book.image
        }
    }

    override fun createNote(book: BookModel,note: NoteModel) {
        val notes = book.notes
        notes.add(note)
    }

    override fun updateNote(book: BookModel,note:NoteModel) {
        val notes = book.notes
        val updatedNoteId = note.id
        val noteToUpdate = notes.find { n -> n.id == updatedNoteId }
        noteToUpdate?.content = note.content
    }

    override fun deleteNote(book: BookModel, note:NoteModel) {
        val notes = book.notes
        val noteToDeleteId = note.id
        val noteToDelete = notes.find { n -> n.id == noteToDeleteId }
        notes.remove(noteToDelete)
    }

    override fun delete(book: BookModel){
        val foundBook: BookModel? = books.find { b -> b.id == book.id }
        if (foundBook != null) {
            books.remove(book)
        }
    }

    override fun getNotes(book: BookModel) : ArrayList<NoteModel> {
        return book.notes
    }

    fun logAll() {
        Timber.v("** Books List **")
        books.forEach { Timber.v("Book ${it}") }
    }

}