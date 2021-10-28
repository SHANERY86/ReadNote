package ie.wit.readnote.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel (var id : Long = 0,
                      var userName : String = "",
                      var password : String = "",
        ) : Parcelable