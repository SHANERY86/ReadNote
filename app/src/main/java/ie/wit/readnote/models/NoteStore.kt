package ie.wit.readnote.models

interface NoteStore {
    fun findAll() : List<NoteModel>
    fun findById(id: Long) : NoteModel?
    fun create(book: BookModel, note: NoteModel)
    fun update(note: NoteModel)
    fun delete(note: NoteModel)
}