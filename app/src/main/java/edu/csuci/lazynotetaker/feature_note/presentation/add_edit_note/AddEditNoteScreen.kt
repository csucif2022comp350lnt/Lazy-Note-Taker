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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import edu.csuci.lazynotetaker.feature_note.domain.model.Note
import edu.csuci.lazynotetaker.feature_note.presentation.MainActivity.Companion.imageFile
import edu.csuci.lazynotetaker.feature_note.presentation.MainActivity.Companion.isFileChooser
import edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note.components.CompleteDialogContent
import edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note.components.ComposeFileProvider
import edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note.components.OCR
import edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note.components.TransparentHintTextField
import edu.csuci.lazynotetaker.ui.theme.Black
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
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
    val pageNumber = viewModel.currentPageNumber.value

    val state = viewModel.state.value

    val scaffoldState = rememberScaffoldState()

    val dialogState: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
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
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    /*val directory = File(context.cacheDir, "images")
                    directory.mkdirs()
                    val file = File(directory,"${Calendar.getInstance().timeInMillis}.png")
                    val uri = FileProvider.getUriForFile(
                        context,
                        context.packageName + ".fileprovider",
                        file
                    )*/

                    val uri = getComposeFileProvider.getImageUri(context)
                    imageUri = uri
                    imageFile = uri
                    Log.i("Uri: ", imageFile.toString())
                    cameraLauncher.launch(uri)
                    ///imagePicker.launch("image/*")
                    state.isColorSectionVisible = false

                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Camera, contentDescription = "Get OCR Text")
            }
        },
        scaffoldState = scaffoldState
    ) { padding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(0.dp)

        )   {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(noteBackgroundAnimatable.value)
                    .padding(2.dp),
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
                    }
                    else {
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
                )  {
                    Icon(
                        Icons.Filled.Save,
                        contentDescription = "Save Note",
                        tint = MaterialTheme.colors.onSurface,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
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
            Spacer(modifier = Modifier.height(5.dp))
//            TransparentHintTextField(
//                text = contentState.text,
//                hint = contentState.hint,
//                onValueChange = {
//                    viewModel.onEvent(AddEditNoteEvent.EnteredContent(it))
//                },
//                onFocusChange = {
//                    viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(it))
//                    state.isColorSectionVisible = false
//                },
//                isHintVisible = contentState.isHintVisible,
//                textStyle = MaterialTheme.typography.body1,
//                modifier = Modifier.fillMaxHeight()
//
//            )
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                val pagerState = rememberPagerState(initialPage = 0)

                HorizontalPager(
                    count = 10,
                    state = pagerState,
                ) {
                    TransparentHintTextField(
                text = contentState.text,
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
                        modifier = Modifier
                            .padding(10.dp)
                            .shadow(1.dp, RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colors.onBackground)
                            .fillMaxWidth()
                            .height(560.dp),


                        )
                }

                PagerIndicator(
                    pagerState = pagerState,
                    indicatorSize = 20.dp,
                    indicatorCount = 7,
                    activeColor = noteBackgroundAnimatable.value,
                    inActiveColor = MaterialTheme.colors.onBackground,
                    indicatorShape = CutCornerShape(10.dp)
                )
            }


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

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerIndicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    indicatorCount: Int = 5,
    indicatorSize: Dp = 16.dp,
    indicatorShape: Shape = CircleShape,
    space: Dp = 8.dp,
    activeColor: Color = Color(0xffEC407A),
    inActiveColor: Color = Color.LightGray,
    onClick: ((Int) -> Unit)? = null
) {

    val listState = rememberLazyListState()

    val totalWidth: Dp = indicatorSize * indicatorCount + space * (indicatorCount - 1)
    val widthInPx = LocalDensity.current.run { indicatorSize.toPx() }

    val currentItem by remember {
        derivedStateOf {
            pagerState.currentPage
        }
    }

    val itemCount = pagerState.pageCount

    LaunchedEffect(key1 = currentItem) {
        val viewportSize = listState.layoutInfo.viewportSize
        listState.animateScrollToItem(
            currentItem,
            (widthInPx / 2 - viewportSize.width / 2).toInt()
        )
    }



    LazyRow(
        modifier = modifier.width(totalWidth),
        state = listState,
        contentPadding = PaddingValues(vertical = space),
        horizontalArrangement = Arrangement.spacedBy(space),
        userScrollEnabled = false
    ) {

        items(itemCount) { index ->

            val isSelected = (index == currentItem)

            // Index of item in center when odd number of indicators are set
            // for 5 indicators this is 2nd indicator place
            val centerItemIndex = indicatorCount / 2

            val right1 =
                (currentItem < centerItemIndex &&
                        index >= indicatorCount - 1)

            val right2 =
                (currentItem >= centerItemIndex &&
                        index >= currentItem + centerItemIndex &&
                        index <= itemCount - centerItemIndex + 1)
            val isRightEdgeItem = right1 || right2

            // Check if this item's distance to center item is smaller than half size of
            // the indicator count when current indicator at the center or
            // when we reach the end of list. End of the list only one item is on edge
            // with 10 items and 7 indicators
            // 7-3= 4th item can be the first valid left edge item and
            val isLeftEdgeItem =
                index <= currentItem - centerItemIndex &&
                        currentItem > centerItemIndex &&
                        index < itemCount - indicatorCount + 1

            Box(
                modifier = Modifier
                    .graphicsLayer {
                        val scale = if (isSelected) {
                            1f
                        } else if (isLeftEdgeItem || isRightEdgeItem) {
                            .5f
                        } else {
                            .8f
                        }
                        scaleX = scale
                        scaleY = scale

                    }

                    .clip(indicatorShape)
                    .size(indicatorSize)
                    .background(
                        if (isSelected) activeColor else inActiveColor,
                        indicatorShape
                    )
                    .then(
                        if (onClick != null) {
                            Modifier
                                .clickable {
                                    onClick.invoke(index)
                                }
                        } else Modifier
                    )
            )
        }
    }
}

