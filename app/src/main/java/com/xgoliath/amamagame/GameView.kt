package com.xgoliath.amamagame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.view.View
import android.view.MotionEvent
import kotlin.math.abs

class GameView(context: Context) : View(context) {
    private val player = Player(100f, 300f, 50f, 50f)
    private val companion = Companion(150f, 300f, 50f, 50f)
    private val gameObjects = mutableListOf<GameObject>()
    private val platforms = mutableListOf<Platform>()
    private val enemies = mutableListOf<Enemy>()
    
    private var lastTime = System.currentTimeMillis()
    private var score = 0
    private var gameRunning = true
    
    private var touchStartX = 0f
    private var touchStartY = 0f
    private var isMovingLeft = false
    private var isMovingRight = false

    init {
        setBackgroundColor(Color.CYAN)
        createLevel()
    }

    private fun createLevel() {
        // Ground
        platforms.add(Platform(0f, 700f, 1280f, 50f, 0))
        
        // Platforms
        platforms.add(Platform(200f, 600f, 200f, 20f, 0))
        platforms.add(Platform(600f, 500f, 200f, 20f, 1))
        platforms.add(Platform(1000f, 400f, 200f, 20f, 0))
        platforms.add(Platform(400f, 350f, 150f, 20f, 1))
        
        gameObjects.addAll(platforms)
        
        // Enemies
        repeat(3) { i ->
            enemies.add(Enemy(200f + i * 300f, 550f, 40f, 40f))
        }
        gameObjects.addAll(enemies)
        gameObjects.add(player)
        gameObjects.add(companion)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        
        if (gameRunning) {
            val currentTime = System.currentTimeMillis()
            val deltaTime = (currentTime - lastTime) / 1000f
            lastTime = currentTime
            
            // Update game state
            updateGame(deltaTime)
            
            // Draw all objects
            for (obj in gameObjects) {
                if (obj.isAlive) {
                    obj.draw(canvas)
                }
            }
            
            // Draw UI
            drawUI(canvas)
        }
        
        postInvalidate()
    }

    private fun updateGame(deltaTime: Float) {
        // Update all game objects
        for (obj in gameObjects) {
            if (obj.isAlive) {
                obj.update(deltaTime)
            }
        }
        
        // Handle collisions
        handleCollisions()
        
        // Remove dead enemies
        enemies.removeAll { it.health <= 0 }
        gameObjects.removeAll { it !is Platform && it !is Player && it !is Companion && !it.isAlive }
        
        // Reset score if player falls off
        if (player.y > height) {
            player.health = 0
        }
    }

    private fun handleCollisions() {
        // Player platform collision
        for (platform in platforms) {
            if (player.collidesWith(platform) && player.velocityY >= 0) {
                if (player.getBottom() - player.velocityY * 0.016f <= platform.getTop()) {
                    player.y = platform.getTop() - player.height
                    player.land()
                }
            }
        }
        
        // Companion platform collision
        for (platform in platforms) {
            if (companion.collidesWith(platform) && companion.velocityY >= 0) {
                if (companion.getBottom() - companion.velocityY * 0.016f <= platform.getTop()) {
                    companion.y = platform.getTop() - companion.height
                    companion.land()
                }
            }
        }
        
        // Player attack enemies
        if (player.isAttacking) {
            for (enemy in enemies) {
                if (player.collidesWith(enemy)) {
                    enemy.takeDamage(25)
                    score += 10
                    if (enemy.health <= 0) {
                        enemy.isAlive = false
                    }
                }
            }
        }
        
        // Enemy attack player
        for (enemy in enemies) {
            if (enemy.collidesWith(player)) {
                player.takeDamage(5)
            }
        }
    }

    private fun drawUI(canvas: Canvas) {
        val paint = android.graphics.Paint().apply {
            color = Color.WHITE
            textSize = 40f
        }
        canvas.drawText("Score: $score", 50f, 50f, paint)
        canvas.drawText("Health: ${player.health}", 50f, 100f, paint)
        canvas.drawText("Enemies: ${enemies.size}", 50f, 150f, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStartX = event.x
                touchStartY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = event.x - touchStartX
                if (abs(dx) > 50) {
                    if (dx < 0) {
                        isMovingLeft = true
                        isMovingRight = false
                    } else {
                        isMovingRight = true
                        isMovingLeft = false
                    }
                }
                
                val dy = event.y - touchStartY
                if (dy < -50 && !player.isJumping) {
                    player.jump()
                    companion.jump()
                }
            }
            MotionEvent.ACTION_UP -> {
                isMovingLeft = false
                isMovingRight = false
                player.stopMovement()
                companion.stopMovement()
            }
        }
        
        if (isMovingLeft) {
            player.moveLeft()
            companion.moveLeft()
        } else if (isMovingRight) {
            player.moveRight()
            companion.moveRight()
        }
        
        return true
    }
}
