package edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note.components

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import java.io.InputStream

class Cropper(context: Context) : ComponentActivity() {

    @Composable
    fun Dialog(Context) {


        val imageCropLauncher = rememberLauncherForActivityResult(CropImageContract()) { result ->
            if (result.isSuccessful) {
                // use the cropped image
                var imageUri = result.uriContent
                Log.i("imageUri res: ", imageUri.toString())
            } else {
                // an error occurred cropping
                val exception = result.error
            }
        }

        val dialogState: MutableState<Boolean> = remember {
            mutableStateOf(false)
        }

        val imagePickerLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            val cropOptions = CropImageContractOptions(uri, CropImageOptions())
            imageCropLauncher.launch(cropOptions)
            val imagefileUri: InputStream? = contentResolver.openInputStream(uri)
            OCR.TesseractOCR(context, imagefileUri)
            dialogState.value = true
        }

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