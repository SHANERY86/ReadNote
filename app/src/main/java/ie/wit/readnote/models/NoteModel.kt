package ie.wit.readnote.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteModel(   var uid : String = "",
                        var content: String = "",
                        var pageNumber: String = "",
                        var nb: Boolean = false
) : Parcelable {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "content" to content,
            "pageNumber" to pageNumber,
            "NB" to nb
        )
    }
}