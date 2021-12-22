package ie.wit.readnote.models

import android.net.Uri
import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookModel(var uid : String? = "",
                     var title: String = "",
                     var image: String = "",
                    var email: String? = "joe@bloggs.com") : Parcelable
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "title" to title,
            "image" to image,
            "email" to email
        )
    }
}