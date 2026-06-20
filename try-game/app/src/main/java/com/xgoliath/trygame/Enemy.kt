package com.xgoliath.trygame

import android.graphics.Bitmap
import android.graphics.Canvas

class Enemy(x: Float, y: Float, loader: SpriteLoader) : NPC(x, y, 64f, 64f, loader) {
    private var idle: Bitmap? = null
    private var direction = -1f
    private var patrolLeft = x - 200f
    private var patrolRight = x + 200f

    init {
        idle = loader.load("duck.png")
        idle?.let { width = it.width.toFloat(); height = it.height.toFloat() }
    }

    override fun update(delta: Long) {
        vx = direction * 100f
        super.update(delta)
        if (x < patrolLeft) direction = 1f
        if (x > patrolRight) direction = -1f
    }

    override fun draw(canvas: Canvas) {
        idle?.let { canvas.drawBitmap(it, x, y, null) } ?: super.draw(canvas)
    }
}
