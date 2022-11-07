package edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note.components

import android.app.Activity
import android.content.Context
import android.content.res.AssetManager
import android.graphics.BitmapFactory
import android.util.Log
import com.googlecode.tesseract.android.TessBaseAPI
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

object OCR : Activity() {
        var text = "Open Camera"

    fun TesseractOCR(context: Context, imageUri: InputStream?): String {
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
            return ""
        }

// Specify image and then recognize it and get result (can be called multiple times during Tesseract lifetime)
        Log.i("Test imageUri", "imageUri: $imageUri")
            //val realImageUri = getUriRealPathAboveKitkat(context, imageUri)

        //val imagefileUri: InputStream? = contentResolver.openInputStream(imageUri)
        val options = BitmapFactory.Options()
        options.inSampleSize =
            4 // 1 - means max size. 4 - means maxsize/4 size. Don't use value <4, because you need more memory in the heap to store your data.
            val bitmap = BitmapFactory.decodeStream(imageUri)
// Specify image and then recognize it and get result (can be called multiple times during Tesseract lifetime)
        if (bitmap == null){
            Log.w("Bitmap failed","Bitmap missing")
            Log.w("Data passed into bitmap", "" + imageUri)
        } else {
            tess.setImage(bitmap)
            text = tess.utF8Text
            return text
        }
// Release Tesseract when you don't want to use it anymore

// Release Tesseract when you don't want to use it anymore
        tess.recycle()
        return ""
    }

}



fun getTessDataPath(context: Context): String {
    return context.filesDir.absolutePath
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

