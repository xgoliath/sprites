package com.xgoliath.trygame

class SpriteAnimator {
    // Lightweight animation helper. Currently unused but ready for sprite-frame animations.
    data class Frame(val assetName: String, val durationMs: Long)

    private val frames = mutableListOf<Frame>()
    private var current = 0
    private var elapsed = 0L

    fun add(frame: Frame) { frames.add(frame) }

    fun update(delta: Long) {
        if (frames.isEmpty()) return
        elapsed += delta
        if (elapsed >= frames[current].durationMs) {
            elapsed = 0
            current = (current + 1) % frames.size
        }
    }

    fun currentFrame(): String? = if (frames.isEmpty()) null else frames[current].assetName
}
