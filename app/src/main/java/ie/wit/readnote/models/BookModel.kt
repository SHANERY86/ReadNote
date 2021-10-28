package ie.wit.readnote.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookModel(var id : Long = 0,
                     var userId: Long = 0,
                     var title: String = "",
                     var image: Uri = Uri.EMPTY,
var notes: ArrayList<NoteModel> = ArrayList()) : Parcelable