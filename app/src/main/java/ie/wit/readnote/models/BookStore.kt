package ie.wit.readnote.models

interface BookStore {
    fun findAll() : List<BookModel>
    fun findById(id: Long) : BookModel?
    fun create(note: BookModel)
}