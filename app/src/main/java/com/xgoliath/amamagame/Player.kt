package com.xgoliath.amamagame

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Color

class Player(x: Float, y: Float, width: Float, height: Float) :
    GameObject(x, y, width, height) {
    
    var isJumping = false
    var isRiding = false
    var isAttacking = false
    var attackCooldown = 0f
    var health = 100
    var speed = 300f
    var jumpForce = 700f
    val gravity = 1500f

    private val paint = Paint().apply {
        color = Color.RED
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

        // Update attack cooldown
        if (attackCooldown > 0) {
            attackCooldown -= deltaTime
        }
    }

    override fun draw(canvas: Canvas) {
        // Draw player as circle (placeholder for sprite)
        canvas.drawCircle(x + width / 2, y + height / 2, width / 2, paint)
        
        // Draw health bar
        val healthPaint = Paint().apply {
            color = Color.GREEN
            strokeWidth = 2f
        }
        canvas.drawRect(x, y - 20, x + width * (health / 100f), y - 10, healthPaint)
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

    fun attack(): Boolean {
        if (attackCooldown <= 0) {
            isAttacking = true
            attackCooldown = 0.5f
            return true
        }
        return false
    }

    fun takeDamage(amount: Int) {
        health -= amount
        if (health < 0) health = 0
    }

    fun land() {
        isJumping = false
        velocityY = 0f
    }
}
