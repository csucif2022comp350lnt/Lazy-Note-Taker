package edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note.components

import android.app.Activity
import android.content.*
import androidx.core.app.ActivityCompat.startActivityForResult

class filePicker {
    val REQUEST_CODE = 100

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            imageView.setImageURI(data?.data) // handle chosen image
        }
    }
}