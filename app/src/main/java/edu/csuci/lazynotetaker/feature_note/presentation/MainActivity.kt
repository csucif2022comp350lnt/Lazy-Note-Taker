package edu.csuci.lazynotetaker.feature_note.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.core.net.toUri
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.googlecode.tesseract.android.TessBaseAPI
import edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note.AddEditNoteScreen
import edu.csuci.lazynotetaker.feature_note.presentation.notes.NotesScreen
import edu.csuci.LazyNoteTaker.feature_note.presentation.util.Screen
import edu.csuci.lazynotetaker.ui.theme.LazyNoteTakerTheme
import dagger.hilt.android.AndroidEntryPoint
import edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note.components.URIPathHelper
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        var text: String = "null"
        var imageUri: Uri = "null".toUri()
        var imageFile: String? = null

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            if (data != null) {
                imageUri = data.data!!
            }
            val imagefileUri: InputStream? = contentResolver.openInputStream(imageUri)
            TesseractOCR(this, imagefileUri)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            LazyNoteTakerTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val context: Context = this
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NotesStreen.route
                    ) {
                        composable(route = Screen.NotesStreen.route) {
                            NotesScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditNoteScreen.route +
                                    "?noteId={noteId}&noteColor={noteColor}",
                            arguments = listOf(
                                navArgument(
                                    name = "noteId"

                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "noteColor"

                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },

                                )

                        ) {
                            val color = it.arguments?.getInt("noteColor") ?: -1
                            AddEditNoteScreen(
                                context = context,
                                navController = navController,
                                noteColor = color,

                                )
                        }
                    }

                }
            }
        }
    }
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
        //val realImageUri = URIPathHelper.getPath(context, imageUri )

        val options = BitmapFactory.Options()
        options.inSampleSize =
            4 // 1 - means max size. 4 - means maxsize/4 size. Don't use value <4, because you need more memory in the heap to store your data.
        val bitmap = BitmapFactory.decodeStream(imageUri)
// Specify image and then recognize it and get result (can be called multiple times during Tesseract lifetime)
        if (bitmap == null){
            Log.w("Bitmap failed","Bitmap missing")
            //Log.w("Data passed into bitmap", "" + realImageUri)
        } else {
            tess.setImage(bitmap)
            MainActivity.text = tess.utF8Text
            return MainActivity.text
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


