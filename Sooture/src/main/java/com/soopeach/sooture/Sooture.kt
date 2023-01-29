package com.soopeach.sooture

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import com.soopeach.sooture.enum.Delimiters
import com.soopeach.sooture.enum.FileExtensions
import com.soopeach.sooture.enum.SootureExceptions
import com.soopeach.sooture.enum.ViewPositions
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 *
 * @author soopeach
 * @constructor for Image File name to be created
 * @since 1.0.0
 *
 */
class Sooture(private var fileName: String) {
    private fun getBitmapFromView(view: View): Bitmap {

        if (view.measuredWidth <= 0 || view.measuredHeight <= 0) {
            throw SootureExceptions.INVALID_VIEW.exception
        }

        val bitmap = Bitmap.createBitmap(
            view.measuredWidth,
            view.measuredHeight,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        view.draw(canvas)

        return bitmap
    }

    /**
     * @param baseView, targetView, targetViewPosition
     * @return Bitmap
     */
    fun combineView(
        baseView: View,
        targetView: View,
        targetViewPosition: ViewPositions = ViewPositions.CENTER
    ): Bitmap {

        val baseBitmap = getBitmapFromView(baseView)
        val targetBitmap =
            getBitmapFromView(targetView)

        val canvas = Canvas(baseBitmap).apply {
            baseView.draw(this)
        }

        val targetCanvas = Canvas(targetBitmap).apply {
            targetView.draw(this)
        }

        canvas.drawBitmap(
            targetBitmap,
            targetViewPosition.left(canvas.width, targetCanvas.width),
            targetViewPosition.top(canvas.height, targetCanvas.height),
            null
        )

        return baseBitmap
    }

    fun cropBitmap(targetBitmap: Bitmap, cropFrame: View): Bitmap {
        return Bitmap.createBitmap(
            targetBitmap, cropFrame.left, cropFrame.top, cropFrame.width, cropFrame.height
        )
    }

    fun saveBitmapToCache(
        context: Context,
        bitmap: Bitmap,
        onSaveSuccess: () -> Unit,
        onSaveFailure: (IOException) -> Unit
    ) {
        saveBitmap(context.cacheDir.path, bitmap, onSaveSuccess, onSaveFailure)
    }

    private fun saveBitmap(
        filePath: String,
        bitmap: Bitmap,
        onSaveSuccess: () -> Unit,
        onSaveFailure: (IOException) -> Unit
    ) {

        val file = File(filePath + Delimiters.SLASH.value + fileName + FileExtensions.PNG.value)
        val out = FileOutputStream(file)

        try {
            out.use {
                file.createNewFile()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            }
            onSaveSuccess()
        } catch (e: IOException) {
            onSaveFailure(e)
        }

    }

    fun changeFileName(newFileName: String) {
        this.fileName = newFileName
    }

}