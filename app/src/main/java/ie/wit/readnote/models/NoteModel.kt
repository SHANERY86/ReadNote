package ie.wit.readnote.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteModel(var id : Long = 0,
                     var content: String = "",
                    var book: BookModel? = null) : Parcelable