package edu.csuci.lazynotetaker.feature_note.presentation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note.AddEditNoteScreen
import edu.csuci.lazynotetaker.feature_note.presentation.notes.NotesScreen
import edu.csuci.LazyNoteTaker.feature_note.presentation.util.Screen
import edu.csuci.lazynotetaker.ui.theme.LazyNoteTakerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        var imageUri: Uri = "null".toUri()
        var imageFile: String? = null

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
}