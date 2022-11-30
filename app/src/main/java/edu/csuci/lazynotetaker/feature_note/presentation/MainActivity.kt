package edu.csuci.lazynotetaker.feature_note.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.core.net.toUri
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note.AddEditNoteScreen
import edu.csuci.lazynotetaker.feature_note.presentation.notes.NotesScreen
import edu.csuci.lazynotetaker.feature_note.presentation.settings.SettingsScreen
import edu.csuci.lazynotetaker.feature_note.presentation.util.Screen
import dagger.hilt.android.AndroidEntryPoint
import edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note.components.OCR.TesseractOCR
import edu.csuci.lazynotetaker.ui.theme.*
import java.io.InputStream

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    companion object {
        var isFileChooser : Boolean = false
        var text: String = "null"
        var imageFile: Uri = "null".toUri()
        var insertText: String = ""
        var appTheme = AmoledColorPalette

    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            lazynotetakerTheme(appTheme) {
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
                        composable(route = Screen.SettingsScreen.route) {
                            SettingsScreen(navController = navController)
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
                            AddEditNoteScreen.AddEditNoteScreen(
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
}
@Composable
fun lazynotetakerTheme(curTheme: Colors, content: @Composable() () -> Unit) {
    MaterialTheme(
        colors = MainActivity.appTheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
