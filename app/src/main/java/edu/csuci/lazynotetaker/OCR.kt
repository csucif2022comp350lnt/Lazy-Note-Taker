package edu.csuci.lazynotetaker

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getSystemService
import com.googlecode.tesseract.android.TessBaseAPI
import java.io.File


object OCR : Activity() {

        var text = ""

    private val capturedImageUri = ComposeFileProvider.imageFile
    @Composable
    fun TesseractOCR(context: Context, language: String?) {
// Create Tesseract instance
        // Create Tesseract instance
        val tess = TessBaseAPI()
        val dataPath: String = File(Environment.DIRECTORY_DOWNLOADS, "tesseract").absolutePath

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

        val bitmap = BitmapFactory.decodeFile(capturedImageUri!!.path, options)
// Specify image and then recognize it and get result (can be called multiple times during Tesseract lifetime)
        tess.setImage(bitmap)
        OCR.text = tess.utF8Text
        println(OCR.text)
        println("Test")

// Release Tesseract when you don't want to use it anymore

// Release Tesseract when you don't want to use it anymore
        tess.recycle()
    }

}

fun downloadFile(fileName : String, url : String){
        val imageLink = Uri.parse(url)
    //val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager?
        var request = DownloadManager.Request(imageLink)
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
            .setAllowedOverRoaming(false)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle(fileName)
            .setDescription("Downloading$fileName")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, File.separator+fileName+".traineddata")
    val downloadId: Long = downloadManager!!.enqueue(request)
}

@Composable
fun ocrUI (
    modifier: Modifier = Modifier,
) {
    Text(
        text = OCR.text
    )
    Button(
        modifier = Modifier.padding(top = 16.dp),
        onClick = {
            downloadFile("eng.traineddata", "https://github.com/tesseract-ocr/tessdata/raw/4.0.0/eng.traineddata" )
        },
    ) {
        Text(
            text = "Download English Model"
        )
    }
}