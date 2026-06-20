package com.xgoliath.trygame

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

open class Entity(var x: Float, var y: Float, var width: Float = 64f, var height: Float = 64f) {
    var vx = 0f
    var vy = 0f
    var paint = Paint()

    open fun update(delta: Long) {
        x += vx * (delta / 1000f)
        y += vy * (delta / 1000f)
    }

    open fun draw(canvas: Canvas) {
        canvas.drawRect(RectF(x, y, x + width, y + height), paint)
    }
}
