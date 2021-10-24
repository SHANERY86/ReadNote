package ie.wit.readnote.models

interface BookStore {
    fun findAll() : List<BookModel>
    fun findById(id: Long) : BookModel?
    fun create(book: BookModel)
    fun update(book: BookModel)
    fun delete(book: BookModel)
    fun addNote(notes: ArrayList<NoteModel>, note: NoteModel)
}