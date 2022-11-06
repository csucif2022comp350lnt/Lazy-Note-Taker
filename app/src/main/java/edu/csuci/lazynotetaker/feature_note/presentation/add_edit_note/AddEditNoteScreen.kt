package edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import edu.csuci.LazyNoteTaker.feature_note.domain.model.Note
import edu.csuci.LazyNoteTaker.feature_note.presentation.add_edit_note.components.TransparentHintTextField
import edu.csuci.lazynotetaker.components.CompleteDialogContent
import edu.csuci.lazynotetaker.feature_note.presentation.MainActivity
import edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note.components.*
import edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note.components.OCR.TesseractOCR
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File

class AddEditNoteScreen (){
    companion object{
        var isCameraorGal: Int = 0
    }
}



fun getFileFromAssets(context: Context, fileName: String): File = File(context.cacheDir, fileName)
    .also {
        if (!it.exists()) {
            it.outputStream().use { cache ->
                context.assets.open(fileName).use { inputStream ->
                    inputStream.copyTo(cache)
                }
            }
        }
    }


@Composable
fun AddEditNoteScreen(
    context: Context,
    navController: NavController,
    noteColor: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val getComposeFileProvider = ComposeFileProvider()
    var hasImage by remember {
        mutableStateOf(false)
    }
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value

    val scaffoldState = rememberScaffoldState()

    val dialogState: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            hasImage = uri != null
            imageUri = uri
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            hasImage = success
        }
    )

    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(if(noteColor != -1) noteColor else viewModel.noteColor.value)

        )
    }
    val scope = rememberCoroutineScope()

    if (dialogState.value) {
        Dialog(
            onDismissRequest = { dialogState.value = false },
            content = {
                //var imageUri: Uri = "null".toUri()
                //val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                //callCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                    Log.e("ImageUri", "ImageUri2$imageUri")
                    CompleteDialogContent("OCR", dialogState, "OK") { BodyContent() }
            },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        )
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is AddEditNoteViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )

                }
                is AddEditNoteViewModel.UiEvent.SavedNote -> {
                    navController.navigateUp()
                }
                else -> {}
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    //MainActivity.requestCamera.launch(android.Manifest.permission.CAMERA)
                    val uri = getComposeFileProvider.getImageUri(context)
                    imageUri = uri
                    cameraLauncher.launch(uri)
                    TesseractOCR(context, uri)
                    dialogState.value = true
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Camera, contentDescription = "Save note")
            }
        },
        scaffoldState = scaffoldState
    ) { padding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimatable.value)
                .padding(padding)
        )   {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Note.noteColors.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.noteColor.value == colorInt) {
                                    Color.Black
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    noteBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500

                                        )
                                    )
                                }
                                viewModel.onEvent(AddEditNoteEvent.ChangeColor(colorInt))
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = titleState.text,
                hint = titleState.hint,
                onValueChange = {
                                viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(it))
                },
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5

            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = contentState.text,
                hint = contentState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredContent(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(it))
                },
                isHintVisible = contentState.isHintVisible,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxHeight()

            )
        }

    }

}

@Composable
fun BodyContent() {
    Text(
        text = OCR.text,
        fontSize = 22.sp
    )
}
