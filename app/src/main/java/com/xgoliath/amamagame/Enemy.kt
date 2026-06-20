package com.xgoliath.amamagame

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Color
import kotlin.random.Random

class Enemy(x: Float, y: Float, width: Float, height: Float) :
    GameObject(x, y, width, height) {
    
    var isJumping = false
    var health = 30
    var speed = 150f
    var jumpForce = 500f
    val gravity = 1500f
    var moveDirection = 1 // 1 for right, -1 for left
    var changeDirectionTimer = 2f
    var jumpTimer = 0f

    private val paint = Paint().apply {
        color = Color.YELLOW
        isAntiAlias = true
    }

    override fun update(deltaTime: Float) {
        // AI movement
        changeDirectionTimer -= deltaTime
        if (changeDirectionTimer <= 0) {
            moveDirection = if (Random.nextFloat() > 0.5f) 1 else -1
            changeDirectionTimer = 2f
        }

        velocityX = moveDirection * speed

        // Random jumping
        jumpTimer -= deltaTime
        if (jumpTimer <= 0 && !isJumping) {
            if (Random.nextFloat() > 0.7f) {
                velocityY = -jumpForce
                isJumping = true
            }
            jumpTimer = 1f
        }

        // Apply gravity
        if (isJumping) {
            velocityY += gravity * deltaTime
        }

        // Update position
        x += velocityX * deltaTime
        y += velocityY * deltaTime
    }

    override fun draw(canvas: Canvas) {
        // Draw enemy as circle (placeholder for sprite)
        canvas.drawCircle(x + width / 2, y + height / 2, width / 2, paint)
    }

    fun takeDamage(amount: Int) {
        health -= amount
    }

    fun land() {
        isJumping = false
        velocityY = 0f
    }
}
