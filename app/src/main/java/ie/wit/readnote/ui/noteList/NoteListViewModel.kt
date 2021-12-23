package ie.wit.readnote.ui.noteList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.readnote.models.FirebaseDBManager
import ie.wit.readnote.models.NoteModel
import timber.log.Timber
import java.lang.Exception

class NoteListViewModel : ViewModel() {
    private val noteList =
        MutableLiveData<List<NoteModel>>()

    val observableNotesList: LiveData<List<NoteModel>>
        get() = noteList

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()


    fun getNotes(userid:String,bookid: String) {
        try {
            //NoteManager.findAll(liveFirebaseUser.value?.email!!, noteList)
            FirebaseDBManager.getBookNotes(userid, bookid,
                noteList)
            Timber.i("NoteList Load Success : ${noteList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("NoteList Load Error : $e.message")
        }
    }

    fun delete(userid: String, id: String) {
        try {
            //NoteManager.delete(userid,id)
//            FirebaseDBManager.delete(userid,id)
            Timber.i("Report Delete Success")
        }
        catch (e: Exception) {
            Timber.i("Report Delete Error : $e.message")
        }
    }
}