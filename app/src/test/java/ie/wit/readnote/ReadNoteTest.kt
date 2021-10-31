package ie.wit.readnote

import android.content.Context
import android.net.Uri
import androidx.test.core.app.ApplicationProvider
import ie.wit.readnote.models.BookModel
import ie.wit.readnote.models.DataJSONStore
import ie.wit.readnote.models.UserModel
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(RobolectricTestRunner::class)
class ReadNoteTest  {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val dataJsonStore = DataJSONStore(context,"test")

    @Test
    fun findUserBooks() {
        val user = UserModel(5255441523788676191,"secret","shanery86")
        val books = dataJsonStore.findUserBooks(user)
        assert(books.size == 2)
        assert(books.get(0).title.equals("Dune"))
    }

    @Test
    fun findBookById() {
        val book = dataJsonStore.findBookById(4762348574826435629)
        assert(book?.title.equals("The Hobbit"))
    }

    @Test
    fun createBook() {
        val book = BookModel(111,999,"Watchmen", Uri.EMPTY, ArrayList())
        assert(dataJsonStore.books.size == 3)
        dataJsonStore.createBook(999,book)
        assert(dataJsonStore.books.size == 4)
    }

    @Test
    fun updateBook() {
        val book = dataJsonStore.findBookById(4762348574826435629)
        assert(book?.title.equals("The Hobbit"))
        book?.title = "The Lord Of the Rings"
        if (book != null) {
            dataJsonStore.updateBook(book)
        }
        val updatedBook = dataJsonStore.findBookById(4762348574826435629)
        assert(updatedBook?.title.equals("The Lord Of the Rings"))
    }

    @Test
    fun deleteBook() {
        val book = dataJsonStore.findBookById(4762348574826435629)
        assert(dataJsonStore.books.size == 3)
        if (book != null) {
            dataJsonStore.deleteBook(book)
        }
        assert(dataJsonStore.books.size == 2)
    }

    @Test
    fun updateNote() {
        val book = dataJsonStore.findBookById(6551500297912303821)
        val notes = book?.notes
        val note = notes?.find { note -> note.id == -403854365118443790}
        assert(note?.content.equals("Lots of Sand"))
        note?.content = "Lots and Lots of Sand"
        if (book != null && note != null) {
            dataJsonStore.updateNote(book, note)
        }
        val updatedNote = notes?.find { note -> note.id == -403854365118443790}
        assert(updatedNote?.content.equals("Lots and Lots of Sand"))
    }

    @Test
    fun deleteNote() {
        val book = dataJsonStore.findBookById(6551500297912303821)
        val notes = book?.notes
        assert(notes?.size == 1)
        val note = notes?.find { note -> note.id == -403854365118443790}
        if (book != null && note != null) {
            dataJsonStore.deleteNote(book, note)
        }
        assert(notes?.size == 0)
    }

    @Test
    fun createUser() {
        assert(dataJsonStore.users.size == 2)
        val user = UserModel(22,"FrankJones","abc123")
        dataJsonStore.createUser(user)
        assert(dataJsonStore.users.size == 3)
    }

    fun deleteUser() {
        assert(dataJsonStore.users.size == 2)
        val user = UserModel(5255441523788676191,"secret","shanery86")
        dataJsonStore.deleteUser(user)
        assert(dataJsonStore.users.size == 1)
    }

}

