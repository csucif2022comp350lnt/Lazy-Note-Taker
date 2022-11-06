package edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note.components

import android.Manifest
import android.content.Context
import pub.devrel.easypermissions.EasyPermissions

object CameraUtility {
    fun hasCameraPermissions(context: Context) =
        EasyPermissions.hasPermissions(
            context,
            Manifest.permission.CAMERA
        )
}