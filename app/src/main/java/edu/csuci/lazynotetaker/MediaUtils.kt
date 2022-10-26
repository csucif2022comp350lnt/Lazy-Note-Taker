package edu.csuci.lazynotetaker

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore

class MediaUtils {
        companion object{

            fun getRealPathFromURI(context: Context, contentUri: Uri?): String? {
                var cursor: Cursor? = null
                return try {
                    val proj =
                        arrayOf(MediaStore.Images.Media.DATA)
                    cursor = context.contentResolver.query(contentUri!!, proj, null, null, null)
                    cursor!!.moveToFirst()
                    val column_index = cursor.getColumnIndex(proj[0])

                    cursor.getString(column_index)
                } finally {
                    cursor?.close()
                }
            }

        }
    }