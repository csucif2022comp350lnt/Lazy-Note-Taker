package edu.csuci.lazynotetaker

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.res.AssetManager
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.util.Log
import androidx.annotation.NonNull
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.googlecode.tesseract.android.TessBaseAPI
import edu.csuci.lazynotetaker.OCR.getDownloadManager
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


object OCR : Activity() {
        var text = "Open Camera"
    fun Context?.getDownloadManager() = this?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?

    @Composable
    fun TesseractOCR(context: Context, imageUri: Uri) {
// Create Tesseract instance
        // Create Tesseract instance
        val tess = TessBaseAPI()
        createDir(context)

// Given path must contain subdirectory `tessdata` where are `*.traineddata` language files

// Given path must contain subdirectory `tessdata` where are `*.traineddata` language files

// Initialize API for specified language (can be called multiple times during Tesseract lifetime)

// Initialize API for specified language (can be called multiple times during Tesseract lifetime)
        if (!tess.init(getTessDataPath(context), "eng")) {
            // Error initializing Tesseract (wrong data path or language)
            tess.recycle()
            print("Test")
            return
        }

// Specify image and then recognize it and get result (can be called multiple times during Tesseract lifetime)
        val options = BitmapFactory.Options()
        options.inSampleSize =
            4 // 1 - means max size. 4 - means maxsize/4 size. Don't use value <4, because you need more memory in the heap to store your data.
            val bitmap = BitmapFactory.decodeFile(imageUri.toString(), options)
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



fun getTessDataPath(@NonNull context: Context): String {
    return context.filesDir.absolutePath;
}

private fun copyFile(
    am: AssetManager, assetName: String,
    outFile: File
) {
    try {
        am.open(assetName).use { `in` ->
            FileOutputStream(outFile).use { out ->
                val buffer = ByteArray(1024)
                var read: Int
                while (`in`.read(buffer).also { read = it } != -1) {
                    out.write(buffer, 0, read)
                }
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

fun createDir(context: Context){
    val am = context.assets

    val tessDir = File(getTessDataPath(context), "tessdata")
    if (!tessDir.exists()) {
        tessDir.mkdir()
    }
    val engFile = File(tessDir, "eng.traineddata")
    if (!engFile.exists()) {
        copyFile(am, "eng.traineddata", engFile)
    }

}

fun downloadFile(fileName : String, url : String, context: Context){
    //val downloadManager = Context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
    val dataPath = File(context.filesDir, "tess")
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
) {
    Text(
        text = OCR.text
    )
}