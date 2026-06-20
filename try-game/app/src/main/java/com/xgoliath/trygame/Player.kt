package com.xgoliath.trygame

import android.graphics.Bitmap
import android.graphics.Canvas

class Player(x: Float, y: Float, private val loader: SpriteLoader) : Entity(x, y, 64f, 64f) {
    private var idle: Bitmap? = null
    private var hasLoaded = false

    init {
        // load lightweight placeholder; actual asset loaded when available
    }

    override fun update(delta: Long) {
        super.update(delta)
        // simple gravity
        vy += 1000f * (delta / 1000f)
        if (y > 1000f) y = 1000f
        if (!hasLoaded) {
            idle = loader.load("standing.png")
            hasLoaded = true
            idle?.let { width = it.width.toFloat(); height = it.height.toFloat() }
        }
    }

    override fun draw(canvas: Canvas) {
        idle?.let {
            canvas.drawBitmap(it, x, y, null)
        } ?: super.draw(canvas)
    }

    fun attack() {
        // placeholder for attack logic
    }
}
