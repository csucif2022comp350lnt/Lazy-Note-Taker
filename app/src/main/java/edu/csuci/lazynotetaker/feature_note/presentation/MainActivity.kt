package edu.csuci.LazyNoteTaker.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import edu.csuci.LazyNoteTaker.feature_note.presentation.add_edit_note.AddEditNoteScreen
import edu.csuci.LazyNoteTaker.feature_note.presentation.notes.NotesScreen
import edu.csuci.LazyNoteTaker.feature_note.presentation.util.Screen
import edu.csuci.LazyNoteTaker.ui.theme.LazyNoteTakerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyNoteTakerTheme {
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
                                noteColor = color
                            )
                        }
                    }

                }
            }
        }
    }
}