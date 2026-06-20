package com.xgoliath.trygame

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class SpriteLoader(private val assets: AssetManager) {
    private val cache = mutableMapOf<String, Bitmap>()

    fun load(name: String, targetHeight: Int? = null): Bitmap? {
        val key = if (targetHeight != null) "$name@$targetHeight" else name
        cache[key]?.let { return it }
        return try {
            val input = assets.open(name)
            var bmp = BitmapFactory.decodeStream(input)
            input.close()
            if (bmp != null && targetHeight != null && bmp.height != targetHeight) {
                val scale = targetHeight.toFloat() / bmp.height.toFloat()
                val newW = (bmp.width * scale).toInt().coerceAtLeast(1)
                val scaled = Bitmap.createScaledBitmap(bmp, newW, targetHeight, true)
                // recycle original if it's different
                if (scaled != bmp) bmp.recycle()
                bmp = scaled
            }
            bmp?.let { cache[key] = it }
            bmp
        } catch (e: Exception) {
            null
        }
    }

    fun clearCache() {
        for ((_, bmp) in cache) {
            try { bmp.recycle() } catch (e: Exception) {}
        }
        cache.clear()
    }
}
