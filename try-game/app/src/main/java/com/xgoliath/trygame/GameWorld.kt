package com.xgoliath.trygame

import android.graphics.Canvas

class GameWorld(private val loader: SpriteLoader) {
    val platforms = mutableListOf<Platform>()
    val enemies = mutableListOf<Enemy>()
    lateinit var player: Player
    lateinit var companion: Companion

    init {
        // simple level: ground + some platforms
        platforms.add(Platform(0f, 1100f, 2000f, 200f))
        platforms.add(Platform(400f, 900f, 300f, 40f))
        platforms.add(Platform(800f, 750f, 300f, 40f))

        // enemies
        enemies.add(Enemy(600f, 1000f, loader))
        enemies.add(Enemy(1000f, 1000f, loader))

        // will set player and companion later from GameSurfaceView
    }

    fun update(delta: Long) {
        player.update(delta)
        companion.update(delta)

        // collisions with platforms
        for (p in platforms) {
            if (player.collidesWith(p)) {
                player.landOn(p)
            }
            if (companion.collidesWith(p)) {
                companion.landOn(p)
            }
        }

        // update enemies
        val iterator = enemies.iterator()
        while (iterator.hasNext()) {
            val e = iterator.next()
            e.update(delta)
            if (player.isAttacking && player.attackHitboxIntersects(e)) {
                e.takeDamage(1)
            }
            if (e.isDead) iterator.remove()
        }

        // companion attack
        if (companion.isAttacking) {
            for (e in enemies) {
                if (companion.attackHitboxIntersects(e)) {
                    e.takeDamage(1)
                }
            }
        }
    }

    fun draw(canvas: Canvas) {
        // simple background
        // draw platforms
        for (p in platforms) p.draw(canvas)
        // draw player, companion, enemies
        player.draw(canvas)
        companion.draw(canvas)
        for (e in enemies) e.draw(canvas)
    }
}
