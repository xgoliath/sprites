package com.xgoliath.trygame

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class HUD {
    private val paint = Paint().apply {
        color = Color.BLACK
        textSize = 40f
    }

    fun draw(canvas: Canvas, hp: Int, score: Int) {
        canvas.drawText("HP: $hp", 20f, 50f, paint)
        canvas.drawText("Score: $score", 20f, 100f, paint)
    }
}
