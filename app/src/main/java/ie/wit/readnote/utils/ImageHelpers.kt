package org.wit.placemark.helpers

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import ie.wit.readnote.R

fun showImagePicker(intentLauncher : ActivityResultLauncher<Intent>) {
    var chooseFile = Intent(Intent.ACTION_OPEN_DOCUMENT)
    chooseFile.type = "image/*"
    chooseFile = Intent.createChooser(chooseFile, R.string.add_coverImage.toString())
    intentLauncher.launch(chooseFile)
}