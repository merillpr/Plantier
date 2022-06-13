package com.lubna.plantier.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

private const val FILENAME_FORMAT = "dd-MMM-yyyy"

//upload foto
fun reduceFileImage(file: File): File {
    val bitmapFile = BitmapFactory.decodeFile(file.path)
    var compressQuality = 100
    var streamLength: Int
    do {
        val bmpStream = ByteArrayOutputStream()
        bitmapFile.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= 5
    } while (streamLength > 1000000)
    bitmapFile.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
    return file
}
//upload foto

fun uriToFile(image: Uri, context: Context): File {
    val content: ContentResolver = context.contentResolver
    val file = customTempFile(context)

    val inStream = content.openInputStream(image) as InputStream
    val outStream: OutputStream = FileOutputStream(file)
    val buf = ByteArray(1024)
    var len: Int
    while (inStream.read(buf).also { len = it } > 0) outStream.write(buf, 0, len)
    outStream.close()
    inStream.close()

    return file
}

fun customTempFile(context: Context): File {
    val storage: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storage)
}

val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())


