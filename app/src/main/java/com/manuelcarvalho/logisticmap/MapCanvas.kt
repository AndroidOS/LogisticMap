package com.manuelcarvalho.logisticmap

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat


private const val TAG = "MapCanvas"

class MapCanvas(context: Context) : View(context) {

    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap

    private var touchX = 0.0f
    private var touchY = 0.0f

    private val mult = 2.6

    private var canvasHeight = 0
    private var canvasWidth = 0

    private var startX = 0.0
    private var startY = 0.0

    private val backgroundColor =
        ResourcesCompat.getColor(resources, R.color.canvasBackground, null)
    private val drawColor = ResourcesCompat.getColor(resources, R.color.canvasColor, null)


    private val paint = Paint().apply {
        color = drawColor
        style = Paint.Style.STROKE
        strokeWidth = 2f
        textSize = 20f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        canvasWidth = w
        canvasHeight = h


        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        extraCanvas.drawColor(backgroundColor)

        generatePoints()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(extraBitmap, 0f, 0f, null)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        touchX = event.x
        touchY = event.y


        return true
    }

    fun generatePoints() {
        var r = 2.6
        var x = 0.4
        for (n in 1..20000) {
            x = r * x * (1 - x) / 1.2
            r += .0001
            displayRealPoint(r, x)
        }

        invalidate()
    }


    fun displayRealPoint(x: Double, y: Double) {

        var x1 = (((x - 2.6) * 450.0)) * 1.1
        var y1 = (y * 1500.0)
        extraCanvas.drawPoint(x1.toFloat(), y1.toFloat(), paint)

        startX = x
        startY = y
        Log.d(TAG, "xy = ${x1} ${y1}")
    }

}