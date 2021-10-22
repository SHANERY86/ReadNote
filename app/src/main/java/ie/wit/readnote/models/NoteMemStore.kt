package ie.wit.readnote.models

import timber.log.Timber

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class NoteMemStore : NoteStore {

    var notes = ArrayList<NoteModel>()
    var book = BookModel()

    override fun findAll(): List<NoteModel> {
        return notes
    }

    override fun findById(id:Long) : NoteModel? {
        val foundNote: NoteModel? = notes.find { it.id == id }
        return foundNote
    }

    override fun create(book: BookModel, note: NoteModel) {
        this.book = book
        this.notes = book.notes
        note.id = getId()
        notes.add(note)
        logAll()
    }

    fun logAll() {
        Timber.v("** Notes List **")
        notes.forEach { Timber.v("Note ${it}") }
    }

}