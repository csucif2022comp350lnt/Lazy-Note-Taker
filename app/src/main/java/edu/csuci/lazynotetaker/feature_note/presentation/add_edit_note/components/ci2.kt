package edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note.components


import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note.components.OCR.getExternalFilesDir
import edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note.components.OCR.startActivityForResult
import java.io.File

class ci2: AppCompatActivity() {
    companion object{
        var imageUri2 = "null"
    }
    fun cameraIntent() {


        var cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100){
            var bmp:Any? = data?.extras?.get("data") as Uri
        }
    }

}