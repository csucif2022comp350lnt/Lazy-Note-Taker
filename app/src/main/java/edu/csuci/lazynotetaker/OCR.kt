package edu.csuci.lazynotetaker

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.kittinunf.fuel.Fuel
import com.googlecode.tesseract.android.TessBaseAPI
import java.io.File

class OCR {

    companion object {
        var text = ""
    }

    val capturedImageUri = ComposeFileProvider.imageFile
    @Composable
    fun TesseractOCR(context: Context, language: String?) {
// Create Tesseract instance
        // Create Tesseract instance
        val tess = TessBaseAPI()

// Given path must contain subdirectory `tessdata` where are `*.traineddata` language files

// Given path must contain subdirectory `tessdata` where are `*.traineddata` language files
        val dataPath = File(Environment.getExternalStorageDirectory(), "tesseract").absolutePath

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

        },
    ) {
        Text(
            text = "Download English Model"
        )
    }
}