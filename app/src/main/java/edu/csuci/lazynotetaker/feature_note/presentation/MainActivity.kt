package edu.csuci.lazynotetaker.feature_note.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note.AddEditNoteScreen
import edu.csuci.lazynotetaker.feature_note.presentation.notes.NotesScreen
import edu.csuci.lazynotetaker.feature_note.presentation.util.Screen
import edu.csuci.lazynotetaker.ui.theme.lazynotetakerTheme
import dagger.hilt.android.AndroidEntryPoint
import edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note.components.OCR.TesseractOCR
import java.io.File
import java.io.InputStream

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // The path to the root of this app's internal storage
    private lateinit var privateRootDir: File
    // The path to the "images" subdirectory
    private lateinit var imagesDir: File
    // Array of files in the images subdirectory
    private lateinit var imageFiles: Array<File>
    // Array of filenames corresponding to imageFiles
    private lateinit var imageFilenames: Array<String>

    companion object {
        var isFileChooser : Boolean = false
        var text: String = "null"
        var imageFile: Uri = "null".toUri()

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            Log.i("datareturn", data.toString())
            if (isFileChooser) {
                imageFile = data!!.data!!
            }
            Log.i("uritofile", imageFile.toString())
            val imagefileUri: InputStream? = contentResolver.openInputStream(imageFile)
            TesseractOCR(this, imagefileUri)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            lazynotetakerTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val context: Context = this
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NotesScreen.route
                    )   {
                        composable(route = Screen.NotesScreen.route) {
                            NotesScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditNoteScreen.route +
                                    "?noteId={noteId}&noteColor={noteColor}",
                            arguments = listOf(
                                navArgument(
                                    name = "noteId"

                                )   {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "noteColor"

                                )   {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },

                            )

                        )   {
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

        // Set up an Intent to send back to apps that request a file
        resultIntent = Intent("com.example.myapp.ACTION_RETURN_FILE")
        // Get the files/ subdirectory of internal storage
        privateRootDir = filesDir
        // Get the files/images subdirectory;
        imagesDir = File(privateRootDir, "images")
        // Get the files in the images subdirectory
        imageFiles = imagesDir.listFiles()
        // Set the Activity's result to null to begin with
        setResult(Activity.RESULT_CANCELED, null)
        /*
         * Display the file names in the ListView fileListView.
         * Back the ListView with the array imageFilenames, which
         * you can create by iterating through imageFiles and
         * calling File.getAbsolutePath() for each File
         */

// Define a listener that responds to clicks on a file in the ListView
        fileListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ -> }

         if (fileUri != null) {
            // Put the Uri and MIME type in the result Intent
             resultIntent.setDataAndType(fileUri, contentResolver.getType(fileUri))
            // Set the result
            setResult(Activity.RESULT_OK, resultIntent)
        } else {
            resultIntent.setDataAndType(null, "")
            setResult(RESULT_CANCELED, resultIntent)
        }

        /*
         * Get a File for the selected file name.
         * Assume that the file names are in the
         * imageFilename array.
         */
        val requestFile = File(imageFilenames[position])
        /*
         * Most file-related method calls need to be in
         * try-catch blocks.
         */
        // Use the FileProvider to get a content URI
        val fileUri: Uri? = try {
            FileProvider.getUriForFile(
                this@MainActivity,
                "com.example.myapp.fileprovider",
                requestFile)
        } catch (e: IllegalArgumentException) {
            Log.e("File Selector",
                "The selected file can't be shared: $requestFile")
            null
        }
    }

    fun onDoneClick(v: View) {
        // Associate a method with the Done button
        finish()
    }
}
