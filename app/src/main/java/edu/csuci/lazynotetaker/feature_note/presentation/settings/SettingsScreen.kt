package edu.csuci.lazynotetaker.feature_note.presentation.settings

import androidx.compose.animation.*
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
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Sort
import androidx.compose.ui.draw.shadow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import edu.csuci.lazynotetaker.feature_note.domain.util.AppTheme
import edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note.AddEditNoteViewModel
import edu.csuci.lazynotetaker.feature_note.presentation.notes.components.NoteItem
import edu.csuci.lazynotetaker.feature_note.presentation.notes.components.OrderSection
import edu.csuci.lazynotetaker.feature_note.presentation.util.Screen
import kotlinx.coroutines.launch
import edu.csuci.lazynotetaker.feature_note.presentation.settings.components.UISection
import edu.csuci.lazynotetaker.ui.theme.lazynotetakerTheme


@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
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
                    .background(MaterialTheme.colors.background)
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.primary
                )

            }
            Spacer(modifier = Modifier.height(1.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(all = 1.dp)
            ) {
                item {

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colors.background)

                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.onEvent(SettingsEvent.ToggleUISection)
                                }
                                .background(MaterialTheme.colors.onBackground)
                                .padding(10.dp),
                            text = "Color Theme",
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.primary
                        )


                    }
                }
                item {
                    AnimatedVisibility(
                        visible = state.isUISectionVisible,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically()
                    ) {
                        UISection(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 0.dp),
                            appTheme = state.appTheme,
                            onThemeChange = {
                                viewModel.onEvent(SettingsEvent.ChangeUITheme)
                            }


                        )


                    }


                }

            }

        }
    }
}