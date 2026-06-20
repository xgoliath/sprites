package com.xgoliath.trygame

import android.graphics.RectF

open class SimpleHitbox(val x: Float, val y: Float, val w: Float, val h: Float) {
    fun intersects(e: Entity): Boolean {
        val r1 = RectF(x, y, x + w, y + h)
        val r2 = RectF(e.x, e.y, e.x + e.width, e.y + e.height)
        return RectF.intersects(r1, r2)
    }
}
