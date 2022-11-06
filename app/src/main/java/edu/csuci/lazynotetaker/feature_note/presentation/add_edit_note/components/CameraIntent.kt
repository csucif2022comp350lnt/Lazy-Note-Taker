package edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note.components.OCR.startActivityForResult
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*



   /* var imageFile: File? = null
    fun cameraIntent(context: Context): Uri {


        try {
            imageFile = createImageFile(context)
            val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (callCameraIntent.resolveActivity(context.packageManager) != null) {
                val authority = context.packageName + ".fileprovider"
                val imageUri =
                    FileProvider.getUriForFile(context, authority, imageFile!!)
                callCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                startActivityForResult(callCameraIntent, 0)
                Log.i("Camera", "Camera intent passed")
            }
        } catch (e: IOException) {
            Log.w("Camera", "Failed to open camera")
        }



        /*if (imageUri != null) {
            OCR.TesseractOCR(context = context, imageUri = imageUri!!)
        }*/
    return "null".toUri()
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Uri? {
        onActivityResult(requestCode, resultCode, data)
        imageUri = data?.extras?.get("data") as Uri
        return imageUri
    }

    fun createImageFile(context: Context): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName: String = "JPG_" + timeStamp + "_"
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (!storageDir!!.exists()) storageDir.mkdirs()
        return File.createTempFile(imageFileName, ".jpg", storageDir)
    }
*/