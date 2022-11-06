package edu.csuci.LazyNoteTaker.feature_note.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import edu.csuci.LazyNoteTaker.feature_note.presentation.notes.NotesScreen
import edu.csuci.LazyNoteTaker.feature_note.presentation.util.Screen
import edu.csuci.lazynotetaker.ui.theme.LazyNoteTakerTheme
import dagger.hilt.android.AndroidEntryPoint
import edu.csuci.lazynotetaker.feature_note.presentation.util.Util
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val tempImgFileName = "xd_temp_img.jpg"
    private lateinit var tempImgUri: Uri


    companion object {
        var imageUri: Uri = "null".toUri()
        var imageFile: String? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val tempImgFile = File(
            getExternalFilesDir(null),
            tempImgFileName
        )
        tempImgUri = FileProvider.getUriForFile(this, "com.xd.camerademokotlin", tempImgFile)


        fun onChangePhotoClicked(view: View) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, tempImgUri)
            cameraResult.launch(intent)
        }

        val cameraResult: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        )
        { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri = tempImgUri

            }
        }


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
