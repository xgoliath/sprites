package com.xgoliath.trygame

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

open class NPC(x: Float, y: Float, width: Float = 64f, height: Float = 64f, protected val loader: SpriteLoader) : Entity(x, y, width, height) {
    var health = 3
    var isDead = false

    fun takeDamage(d: Int) {
        health -= d
        if (health <= 0) isDead = true
    }

    open fun attack() {}

    override fun draw(canvas: Canvas) {
        paint.color = android.graphics.Color.RED
        super.draw(canvas)
    }
}
