package ie.wit.readnote.firebase

import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import ie.wit.readnote.models.BookModel
import org.wit.placemark.helpers.readImageFromPath
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File

class FirebaseImageManager(val context: Context) {
    var database: DatabaseReference = FirebaseDatabase.getInstance().reference
    var storage: StorageReference = FirebaseStorage.getInstance().reference

    fun updateCoverImage(userid: String, book: BookModel) {
        if (book.image != "") {
            val fileName = File(book.image)
            val imageName = fileName.getName()

            var imageRef = storage.child(userid + '/' + imageName)
            val baos = ByteArrayOutputStream()
            val bitmap = readImageFromPath(context, book.image)

            bitmap?.let {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                val uploadTask = imageRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    println(it.message)
                }.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        book.image = it.toString()
                        Timber.i("TEST URL: ${it}")
                        book.uid?.let { it1 -> database.child("user-books").child(userid).child(it1).setValue(book) }
                    }
                }
            }
        }
    }
}