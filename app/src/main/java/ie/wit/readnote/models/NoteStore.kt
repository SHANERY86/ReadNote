package ie.wit.readnote.models

interface NoteStore {
    fun findAll() : List<NoteModel>
    fun findById(id: Long) : NoteModel?
    fun create(note: NoteModel)
}