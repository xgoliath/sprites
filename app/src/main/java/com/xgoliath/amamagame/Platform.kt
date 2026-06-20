package com.xgoliath.amamagame

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Color

class Platform(x: Float, y: Float, width: Float, height: Float, private val type: Int) :
    GameObject(x, y, width, height) {
    
    private val paint = Paint().apply {
        color = when (type) {
            0 -> Color.GREEN
            1 -> Color.YELLOW
            else -> Color.GRAY
        }
        isAntiAlias = true
    }

    override fun update(deltaTime: Float) {
        // Platforms don't move
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRect(x, y, x + width, y + height, paint)
    }
}
