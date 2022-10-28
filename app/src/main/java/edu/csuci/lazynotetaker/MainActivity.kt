package edu.csuci.lazynotetaker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import edu.csuci.lazynotetaker.ui.theme.LazyNoteTakerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyNoteTakerTheme {
                // A surface container using the 'background' color from the theme
                //ImagePicker(this)
                ComposeCameraIntent(this)
                OcrUI()
            }
        }
    }
}
