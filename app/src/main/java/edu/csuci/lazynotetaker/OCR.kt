package edu.csuci.lazynotetaker

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.googlecode.tesseract.android.TessBaseAPI
import edu.csuci.lazynotetaker.OCR.getDownloadManager
import java.io.File


object OCR : Activity() {

        var text = ""
    fun Context?.getDownloadManager() = this?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?

    private val capturedImageUri = ComposeFileProvider.imageFile
    @Composable
    fun TesseractOCR(context: Context) {
// Create Tesseract instance
        // Create Tesseract instance
        val tess = TessBaseAPI()
        checkForDir(context)
        val dataPath: String = File(context.filesDir, "tessdata").absolutePath

// Given path must contain subdirectory `tessdata` where are `*.traineddata` language files

// Given path must contain subdirectory `tessdata` where are `*.traineddata` language files

// Initialize API for specified language (can be called multiple times during Tesseract lifetime)

// Initialize API for specified language (can be called multiple times during Tesseract lifetime)
        if (!tess.init(dataPath, "eng")) {
            // Error initializing Tesseract (wrong data path or language)
            tess.recycle()
            return
        }

// Specify image and then recognize it and get result (can be called multiple times during Tesseract lifetime)
        val options = BitmapFactory.Options()
        options.inSampleSize =
            4 // 1 - means max size. 4 - means maxsize/4 size. Don't use value <4, because you need more memory in the heap to store your data.

        val bitmap = BitmapFactory.decodeFile(capturedImageUri.toString(), options)
// Specify image and then recognize it and get result (can be called multiple times during Tesseract lifetime)
        tess.setImage(bitmap)
        text = tess.utF8Text
        println(text)
        println("Test")

// Release Tesseract when you don't want to use it anymore

// Release Tesseract when you don't want to use it anymore
        tess.recycle()
    }

}

fun checkForDir(context: Context){
    val tessdir = File(context.filesDir, "tessdata")
    if(!tessdir.exists()){
    tessdir.mkdirs()

    }

}

fun downloadFile(fileName : String, url : String, context: Context){
    //val downloadManager = Context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
    val dataPath = File(context.filesDir, "tessdata")
    val downloadUri = Uri.parse(url)
    val request = DownloadManager.Request(downloadUri)
    request.setTitle(fileName)
    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
    request.setDestinationInExternalFilesDir(
        context,
        dataPath.toString(),
        fileName
    )

    val downloadId = context.getDownloadManager()!!.enqueue(request)
}

@Composable
fun OcrUI (
    Context: Context
) {
    Text(
        text = OCR.text
    )
    Button(
        modifier = Modifier.padding(top = 16.dp),
        onClick = {
            downloadFile("eng.traineddata", "https://github.com/tesseract-ocr/tessdata/raw/4.0.0/eng.traineddata", Context )
        },
    ) {
        Text(
            text = "Download English Model"
        )
    }
}