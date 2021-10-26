package ie.wit.readnote.models

interface BookStore {
    fun findAll() : List<BookModel>
    fun findById(id: Long) : BookModel?
    fun create(book: BookModel)
    fun update(book: BookModel)
    fun delete(book: BookModel)
    fun createNote(book: BookModel, note: NoteModel)
    fun updateNote(book: BookModel, note: NoteModel)
    fun deleteNote(book: BookModel, note: NoteModel)
    fun getNotes(book: BookModel): ArrayList<NoteModel>
}