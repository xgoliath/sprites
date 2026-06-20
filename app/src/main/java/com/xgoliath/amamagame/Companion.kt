package com.xgoliath.amamagame

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Color

class Companion(x: Float, y: Float, width: Float, height: Float) :
    GameObject(x, y, width, height) {
    
    var isJumping = false
    var hasPlayer = false
    var speed = 250f
    var jumpForce = 600f
    val gravity = 1500f

    private val paint = Paint().apply {
        color = Color.BLUE
        isAntiAlias = true
    }

    override fun update(deltaTime: Float) {
        // Apply gravity
        if (isJumping) {
            velocityY += gravity * deltaTime
        }

        // Update position
        x += velocityX * deltaTime
        y += velocityY * deltaTime
    }

    override fun draw(canvas: Canvas) {
        // Draw companion as circle (placeholder for sprite)
        canvas.drawCircle(x + width / 2, y + height / 2, width / 2, paint)
    }

    fun moveLeft() {
        velocityX = -speed
    }

    fun moveRight() {
        velocityX = speed
    }

    fun stopMovement() {
        velocityX = 0f
    }

    fun jump() {
        if (!isJumping) {
            velocityY = -jumpForce
            isJumping = true
        }
    }

    fun land() {
        isJumping = false
        velocityY = 0f
    }
}
