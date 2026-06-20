package com.xgoliath.trygame

import android.graphics.Bitmap

class Companion(x: Float, y: Float, loader: SpriteLoader) : Entity(x, y, 48f, 48f) {
    private var idle: Bitmap? = null
    var followTarget: Player? = null
    var isRidden = false
    var isAttacking = false

    init {
        idle = loader.load("marten-standing.png")
        idle?.let { width = it.width.toFloat(); height = it.height.toFloat() }
    }

    override fun update(delta: Long) {
        super.update(delta)
        followTarget?.let { t ->
            if (!isRidden) {
                // simple follow
                val dx = (t.x - x - 50f)
                vx = (dx * 2f) / 1000f * delta
            } else {
                // when ridden, stay on player
                x = t.x + 10f
                y = t.y
            }
        }
    }

    override fun draw(canvas: android.graphics.Canvas) {
        idle?.let { canvas.drawBitmap(it, x, y, null) } ?: super.draw(canvas)
    }

    fun attack() {
        isAttacking = true
    }

    fun attackHitboxIntersects(e: Enemy): Boolean {
        // simple AABB
        val left = x
        val right = x + width
        val top = y
        val bottom = y + height
        return !(right < e.x || left > e.x + e.width || bottom < e.y || top > e.y + e.height)
    }

    fun collidesWith(p: Platform): Boolean {
        val bottom = y + height
        return bottom >= p.y && bottom <= p.y + 50 && x + width > p.x && x < p.x + p.width
    }

    fun landOn(p: Platform) {
        y = p.y - height
        vy = 0f
    }
}
