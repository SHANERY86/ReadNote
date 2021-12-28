package ie.wit.readnote.ui.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.readnote.models.FirebaseDBManager
import ie.wit.readnote.models.NoteModel
import timber.log.Timber
import java.lang.Exception

class NoteViewModel : ViewModel() {
    private val note = MutableLiveData<NoteModel>()

    var observableNote: LiveData<NoteModel>
        get() = note
        set(value) {note.value = value.value}

    fun getNote(userid:String, id: String) {
        try {
//            FirebaseDBManager.findById(userid, id, note)
            Timber.i("Detail getNote() Success : ${
                note.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Detail getNote() Error : $e.message")
        }
    }

    fun makeNote(userid:String,bookid: String, note: NoteModel) {
        try {
            FirebaseDBManager.createNote(userid, bookid, note)
            Timber.i("Detail update() Success : $note")
        }
        catch (e: Exception) {
            Timber.i("Detail update() Error : $e.message")
        }
    }
}