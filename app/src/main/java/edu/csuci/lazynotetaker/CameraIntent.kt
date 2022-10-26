package edu.csuci.lazynotetaker

import android.content.Context
import android.content.Intent
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import edu.csuci.lazynotetaker.OCR.startActivityForResult
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CameraIntent: FileProvider() {
    companion object {
        var imageFile: File? = null
    }
}
    @Composable
    fun ComposeCameraIntent(Context: Context){
        val context = LocalContext.current

            Column(
                modifier = Modifier
                    .padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Button(
                    modifier = Modifier.padding(top = 16.dp),
                    onClick = {
try {
    CameraIntent.imageFile = createImageFile()
    val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    if (callCameraIntent.resolveActivity(context.packageManager) != null){
        val authority = context.packageName + ".fileprovider"
        val imageUri = FileProvider.getUriForFile(context, authority, CameraIntent.imageFile!!)
        callCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(callCameraIntent, 0)
    }
}                   catch (e: IOException){
    print("Fail")
}
                    }
                ){
                    Text(
                        text = "Intent"
                    )
                }
            }
    }
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        onActivityResult(requestCode, resultCode, data)
    }
    fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName: String = "JPG_" + timeStamp + "_"
        val storageDir: File? = OCR.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (!storageDir!!.exists()) storageDir.mkdirs()
        return File.createTempFile(imageFileName, ".jpg", storageDir)
    }
