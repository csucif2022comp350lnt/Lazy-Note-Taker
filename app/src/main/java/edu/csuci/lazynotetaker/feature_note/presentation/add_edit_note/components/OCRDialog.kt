package edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.csuci.lazynotetaker.feature_note.presentation.MainActivity
import edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note.AddEditNoteEvent
import edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note.AddEditNoteViewModel

@Composable
    fun CompleteDialogContent(
        title: String,
        dialogState: MutableState<Boolean>,
        successButtonText: String,
        viewModel: AddEditNoteViewModel = hiltViewModel(),
        content: @Composable () -> Unit
    ) {
        Card(
            modifier = Modifier
                .fillMaxHeight(0.9f)
                .fillMaxWidth(1f),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                TitleAndButton(title, dialogState)
                AddBody(content)
                BottomButtons(successButtonText, dialogState = dialogState, viewModel)
            }
        }
    }

    @Composable
    private fun TitleAndButton(title: String, dialogState: MutableState<Boolean>) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title, fontSize = 24.sp)
                IconButton(modifier = Modifier.then(Modifier.size(24.dp)),
                    onClick = {
                        dialogState.value = false
                    }) {
                    Icon(
                        Icons.Filled.Close,
                        "contentDescription"
                    )
                }
            }
            Divider(color = Color.DarkGray, thickness = 1.dp)
        }
    }

    @Composable
    private fun BottomButtons(successButtonText: String, dialogState: MutableState<Boolean>, viewModel: AddEditNoteViewModel = hiltViewModel()) {
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxWidth(1f)
                .padding(20.dp),
            horizontalArrangement = Arrangement.Center
        ) {

            Button(
                onClick = {
                    MainActivity.insertText = OCR.text
                    dialogState.value = false
                },
                modifier = Modifier.width(100.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Insert", fontSize = 20.sp)
            }

        }
    }

    @Composable
    private fun AddBody(content: @Composable () -> Unit) {
        Box(
            modifier = Modifier
                .padding(20.dp)
        ) {
            content()
        }
    }