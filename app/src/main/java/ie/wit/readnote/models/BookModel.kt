package ie.wit.readnote.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookModel(var id : Long = 0,
                     var title: String = "",
var notes: List<NoteModel>) : Parcelable