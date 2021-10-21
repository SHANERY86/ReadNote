package ie.wit.readnote.models

import timber.log.Timber

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class NoteMemStore : NoteStore {

    val notes = ArrayList<NoteModel>()

    override fun findAll(): List<NoteModel> {
        return notes
    }

    override fun findById(id:Long) : NoteModel? {
        val foundNote: NoteModel? = notes.find { it.id == id }
        return foundNote
    }

    override fun create(note: NoteModel) {
        note.id = getId()
        notes.add(note)
        logAll()
    }

    fun logAll() {
        Timber.v("** Notes List **")
        notes.forEach { Timber.v("Donate ${it}") }
    }

}