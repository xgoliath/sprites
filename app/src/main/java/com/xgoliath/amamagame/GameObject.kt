package com.xgoliath.amamagame

import android.graphics.Canvas
import android.graphics.Bitmap

abstract class GameObject(var x: Float, var y: Float, var width: Float, var height: Float) {
    var velocityX = 0f
    var velocityY = 0f
    var isAlive = true

    abstract fun update(deltaTime: Float)
    abstract fun draw(canvas: Canvas)

    fun getLeft() = x
    fun getRight() = x + width
    fun getTop() = y
    fun getBottom() = y + height

    fun collidesWith(other: GameObject): Boolean {
        return !(this.getRight() < other.getLeft() ||
                this.getLeft() > other.getRight() ||
                this.getBottom() < other.getTop() ||
                this.getTop() > other.getBottom())
    }
}
