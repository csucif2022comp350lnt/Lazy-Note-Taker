package edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import edu.csuci.lazynotetaker.feature_note.domain.model.Note
import edu.csuci.lazynotetaker.feature_note.presentation.MainActivity
import edu.csuci.lazynotetaker.feature_note.presentation.MainActivity.Companion.imageFile
import edu.csuci.lazynotetaker.feature_note.presentation.MainActivity.Companion.isFileChooser
import edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note.components.*
import edu.csuci.lazynotetaker.ui.theme.Black
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

object AddEditNoteScreen: ComponentActivity() {

    @Composable
    fun AddEditNoteScreen(
        context: Context,
        navController: NavController,
        noteColor: Int,
        viewModel: AddEditNoteViewModel = hiltViewModel()
    ) {

        val dialogState: MutableState<Boolean> = remember {
            mutableStateOf(false)
        }

        val imageCropLauncher = rememberLauncherForActivityResult(CropImageContract()) { result ->
            if (result.isSuccessful) {
                // use the cropped image
                //Log.i("imageUri res: ", imageUri.toString())
                var resultImageUri = result.uriContent
                var imageFilePath = result.getUriFilePath(context = context, uniqueName = true).toString()
                if (resultImageUri?.scheme?.contains("content") == true) {
                    // replace Scheme to file
                    val builder = Uri.Builder()
                    builder.scheme("file")
                        .appendPath(imageFilePath)
                    val imageUri = builder.build()
                }
                Log.i("imageUri: ", imageFilePath.toString())
                //val imagefileUri: InputStream? = contentResolver.openInputStream(imageUri)
                OCR.TesseractOCR(context, imageFilePath.toString())
                dialogState.value = true
            } else {
                // an error occurred cropping
                val exception = result.error
            }
        }

        val imagePickerLauncher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
                val cropOptions = CropImageContractOptions(uri, CropImageOptions())
                imageCropLauncher.launch(cropOptions)

            }

        val getComposeFileProvider = ComposeFileProvider()
        var hasImage by remember {
            mutableStateOf(false)
        }
        var imageUri by remember {
            mutableStateOf<Uri?>(null)
        }
        val titleState = viewModel.noteTitle.value
        val contentState = viewModel.noteContent.value

        val state = viewModel.state.value

        val scaffoldState = rememberScaffoldState()


        val imagePicker = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri ->
                hasImage = uri != null
                imageUri = uri
                isFileChooser = true
                dialogState.value = true
            }
        )

        val cameraLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture(),
            onResult = { success ->
                hasImage = success
                isFileChooser = false
                dialogState.value = true
            }
        )


        val noteBackgroundAnimatable = remember {
            Animatable(
                Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)

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
                when (event) {
                    is AddEditNoteViewModel.UiEvent.ShowSnackbar -> {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = event.message
                        )

                    }
                    is AddEditNoteViewModel.UiEvent.SavedNote -> {
                        navController.navigateUp()
                    }
                }
            }
        }

        Scaffold(

            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        val uri = getComposeFileProvider.getImageUri(context)
                        imageUri = uri
                        imageFile = uri
                        Log.i("Uri: ", imageFile.toString())

                        imagePickerLauncher.launch("image/*")
                        ///imagePicker.launch("image/*")
                        state.isColorSectionVisible = false

                    },
                    backgroundColor = MaterialTheme.colors.primary
                ) {
                    Icon(imageVector = Icons.Default.Camera, contentDescription = "Get OCR Text")
                }
            },
            scaffoldState = scaffoldState
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
                    .padding(0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(noteBackgroundAnimatable.value)
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (state.isColorSectionVisible) {
                        Note.noteColors.forEach { color ->
                            val colorInt = color.toArgb()
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .shadow(15.dp, CircleShape)
                                    .background(color)
                                    .border(
                                        width = 3.dp,
                                        color = if (viewModel.noteColor.value == colorInt) {
                                            Black
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
                    } else {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .shadow(15.dp, CircleShape)
                                .background(noteBackgroundAnimatable.value)
                                .border(
                                    width = 3.dp,
                                    color = MaterialTheme.colors.onBackground,
                                    shape = CircleShape
                                )
                                .clickable {
                                    viewModel.onEvent(AddEditNoteEvent.ToggleColorSection)
                                }

                        )
                    }

                    IconButton(
                        onClick = {
                            viewModel.onEvent(AddEditNoteEvent.SaveNote)
                        }
                    ) {
                        Icon(
                            Icons.Filled.Save,
                            contentDescription = "Save Note",
                            tint = MaterialTheme.colors.onSurface,
                            modifier = Modifier.size(30.dp)
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
                        state.isColorSectionVisible = false
                    },
                    isHintVisible = titleState.isHintVisible,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.h5


                )

                Spacer(modifier = Modifier.height(16.dp))
                TransparentHintTextField(
                    text = contentState.text + MainActivity.insertText,
                    hint = contentState.hint,
                    onValueChange = {
                        viewModel.onEvent(AddEditNoteEvent.EnteredContent(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(it))
                        state.isColorSectionVisible = false
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
        SelectionContainer() {
            Text(
                text = OCR.text,
                fontSize = 22.sp
            )
        }

    }

}