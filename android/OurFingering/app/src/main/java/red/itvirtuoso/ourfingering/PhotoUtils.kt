package red.itvirtuoso.ourfingering

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface

object PhotoUtils {
    fun createBitmap(targetW: Int, targetH: Int, photoPath: String): Bitmap {
        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(photoPath, bmOptions)
        val photoW = bmOptions.outWidth
        val photoH = bmOptions.outHeight
        val scaleFactor = Math.min(photoW / targetW, photoH / targetH)
        bmOptions.inJustDecodeBounds = false
        bmOptions.inSampleSize = scaleFactor
        val bitmapTemp = BitmapFactory.decodeFile(photoPath, bmOptions)

        val exif = ExifInterface(photoPath)
        val rotate = when (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
            ExifInterface.ORIENTATION_ROTATE_90 -> {
                90f
            }
            ExifInterface.ORIENTATION_ROTATE_180 -> {
                180f
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> {
                270f
            }
            else -> {
                0f
            }
        }

        val matrix = Matrix()
        matrix.postRotate(rotate)
        val bitmap = Bitmap.createBitmap(bitmapTemp, 0, 0, bitmapTemp.width, bitmapTemp.height, matrix, true)

        return bitmap
    }
}