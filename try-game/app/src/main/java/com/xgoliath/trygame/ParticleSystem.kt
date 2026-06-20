package com.xgoliath.trygame

import android.graphics.Canvas
import android.graphics.Paint

class ParticleSystem {
    private val particles = mutableListOf<Particle>()
    private val paint = Paint()

    fun spawn(x: Float, y: Float, count: Int = 12) {
        for (i in 0 until count) {
            val angle = Math.random() * Math.PI * 2
            val speed = (Math.random() * 200 + 50).toFloat()
            val vx = (Math.cos(angle) * speed).toFloat()
            val vy = (Math.sin(angle) * speed).toFloat()
            val life = (500 + Math.random() * 500).toLong()
            val r = (4 + Math.random() * 6).toFloat()
            val color = 0xFFFFA500.toInt() // orange-ish
            particles.add(Particle(x, y, vx, vy, life, r, color))
        }
    }

    fun update(delta: Long) {
        val it = particles.iterator()
        while (it.hasNext()) {
            val p = it.next()
            p.lifeMs -= delta
            if (p.lifeMs <= 0) { it.remove(); continue }
            val dt = delta / 1000f
            p.x += p.vx * dt
            p.y += p.vy * dt
            // simple gravity
            p.vy += 800f * dt
        }
    }

    fun draw(canvas: Canvas) {
        for (p in particles) {
            paint.color = p.color
            val alpha = ((p.lifeMs.coerceAtLeast(0L).toFloat() / 1000f) * 255).toInt().coerceIn(0,255)
            paint.alpha = alpha
            canvas.drawCircle(p.x, p.y, p.radius, paint)
        }
    }
}
