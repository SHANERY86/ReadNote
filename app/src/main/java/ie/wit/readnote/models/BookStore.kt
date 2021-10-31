package ie.wit.readnote.models

interface BookStore {
    fun findUserBooks(user: UserModel) : List<BookModel>
    fun findBookById(id: Long) : BookModel?
    fun createBook(userId: Long, book: BookModel)
    fun updateBook(book: BookModel)
    fun deleteBook(book: BookModel)
    fun createNote(book: BookModel, note: NoteModel)
    fun updateNote(book: BookModel, note: NoteModel)
    fun deleteNote(book: BookModel, note: NoteModel)
}