package com.xgoliath.amamagame

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class AttackEffect(x: Float, y: Float) : GameObject(x, y, 40f, 40f) {
    private var lifetime = 0.3f
    private val paint = Paint().apply {
        color = Color.RED
        style = Paint.Style.STROKE
        strokeWidth = 3f
    }

    override fun update(deltaTime: Float) {
        lifetime -= deltaTime
        if (lifetime <= 0) {
            isAlive = false
        }
    }

    override fun draw(canvas: Canvas) {
        canvas.drawCircle(x + width / 2, y + height / 2, width / 2, paint)
    }
}
