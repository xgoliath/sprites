package com.xgoliath.trygame

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class SpriteLoader(private val assets: AssetManager) {
    private val cache = mutableMapOf<String, Bitmap>()

    fun load(name: String): Bitmap? {
        cache[name]?.let { return it }
        return try {
            val input = assets.open(name)
            val bmp = BitmapFactory.decodeStream(input)
            input.close()
            cache[name] = bmp
            bmp
        } catch (e: Exception) {
            null
        }
    }
}
