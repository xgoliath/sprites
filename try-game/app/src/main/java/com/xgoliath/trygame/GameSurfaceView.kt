package com.xgoliath.trygame

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.MotionEvent

class GameSurfaceView(context: Context, attrs: AttributeSet? = null) : SurfaceView(context, attrs), SurfaceHolder.Callback {
    private var thread: GameLoop? = null
    private val spriteLoader = SpriteLoader(context.assets)
    private val player = Player(100f, 300f, spriteLoader)

    constructor(context: Context) : this(context, null)

    init {
        holder.addCallback(this)
        isFocusable = true
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        thread = GameLoop(holder, this)
        thread?.running = true
        thread?.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        thread?.running = false
        while (retry) {
            try {
                thread?.join()
                retry = false
            } catch (e: InterruptedException) {
            }
        }
    }

    fun update(delta: Long) {
        player.update(delta)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // clear background
        canvas.drawRGB(135, 206, 235) // sky blue
        player.draw(canvas)
    }

    fun pause() {
        thread?.running = false
    }

    fun resume() {
        if (thread == null || !thread!!.running) {
            thread = GameLoop(holder, this)
            thread?.running = true
            thread?.start()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        // placeholder: on touch, player attacks
        if (event?.action == MotionEvent.ACTION_DOWN) {
            player.attack()
        }
        return true
    }
}
