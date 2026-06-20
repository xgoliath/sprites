package com.xgoliath.trygame

import android.view.SurfaceHolder

class GameLoop(private val surfaceHolder: SurfaceHolder, private val gameView: GameSurfaceView) : Thread() {
    @Volatile
    var running = false

    private val targetFPS = 60
    private val targetTime = (1000 / targetFPS).toLong()

    override fun run() {
        var startTime: Long
        var timeMillis: Long
        var waitTime: Long

        while (running) {
            startTime = System.nanoTime()

            synchronized(surfaceHolder) {
                val canvas = try { surfaceHolder.lockCanvas() } catch (e: Exception) { null }
                if (canvas != null) {
                    try {
                        val now = System.currentTimeMillis()
                        gameView.update(targetTime)
                        gameView.draw(canvas)
                    } finally {
                        surfaceHolder.unlockCanvasAndPost(canvas)
                    }
                }
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000
            waitTime = targetTime - timeMillis
            if (waitTime > 0) {
                try {
                    sleep(waitTime)
                } catch (e: InterruptedException) {
                }
            }
        }
    }
}
