package edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.darkColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun TransparentHintTextField(
    text: String,
    hint: String,
    modifier: Modifier = Modifier,
    isHintVisible: Boolean = true,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false,
    onFocusChange: (FocusState) -> Unit
    ) {

    Box(
        modifier = modifier
            .padding(8.dp)
    ) {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = textStyle.copy(color = MaterialTheme.colors.primary),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    onFocusChange(it)
                }

        )
        if (isHintVisible) {
            Text(text = hint, style = textStyle, color = Color.LightGray)
        }
    }
}

