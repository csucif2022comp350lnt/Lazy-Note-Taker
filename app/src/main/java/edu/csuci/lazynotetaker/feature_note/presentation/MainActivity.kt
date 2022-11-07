package edu.csuci.lazynotetaker.feature_note.presentation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note.AddEditNoteScreen
import edu.csuci.lazynotetaker.feature_note.presentation.notes.NotesScreen
import edu.csuci.lazynotetaker.feature_note.presentation.util.Screen
import edu.csuci.lazynotetaker.ui.theme.lazynotetakerTheme
import dagger.hilt.android.AndroidEntryPoint
import edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note.components.OCR.TesseractOCR
import java.io.InputStream

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        var text: String = "null"
        var imageUri: Uri = "null".toUri()

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
            lazynotetakerTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {

                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NotesStreen.route
                    )   {
                        composable(route = Screen.NotesStreen.route) {
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
                                navController = navController,
                                noteColor = color,

                            )
                        }
                    }

                }
            }
        }
    }
}
