package com.xgoliath.trygame

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect

class AnimatedSprite(private val loader: SpriteLoader, assetName: String, private val frameDurationMs: Long = 100L) {
    private val frames = mutableListOf<Bitmap>()
    private var currentIndex = 0
    private var elapsed = 0L

    init {
        val bmp = loader.load(assetName) ?: return
        // if the image is wider than tall and width is multiple of height, treat as horizontal sprite sheet
        if (bmp.width > bmp.height && bmp.width % bmp.height == 0) {
            val frameCount = bmp.width / bmp.height
            val frameW = bmp.height
            for (i in 0 until frameCount) {
                val f = Bitmap.createBitmap(bmp, i * frameW, 0, frameW, bmp.height)
                frames.add(f)
            }
            // free the original full bitmap
            if (frames.isNotEmpty()) bmp.recycle()
        } else {
            frames.add(bmp)
        }
    }

    fun update(delta: Long) {
        if (frames.size <= 1) return
        elapsed += delta
        if (elapsed >= frameDurationMs) {
            elapsed = 0
            currentIndex = (currentIndex + 1) % frames.size
        }
    }

    fun draw(canvas: Canvas, x: Float, y: Float) {
        if (frames.isEmpty()) return
        val bmp = frames[currentIndex]
        canvas.drawBitmap(bmp, x, y, null)
    }

    fun width(): Int = if (frames.isEmpty()) 0 else frames[0].width
    fun height(): Int = if (frames.isEmpty()) 0 else frames[0].height
}
