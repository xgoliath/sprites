package com.xgoliath.trygame

import android.content.Context
import android.media.MediaPlayer
import android.media.SoundPool
import android.util.Log

class SoundManager(private val context: Context) {
    private val soundPool: SoundPool = SoundPool.Builder().setMaxStreams(4).build()
    private val sounds = HashMap<String, Int>()
    private var bgPlayer: MediaPlayer? = null

    fun preloadBackground(assetName: String) {
        // don't start it yet, just ensure the asset exists
        try {
            context.assets.open(assetName).close()
        } catch (e: Exception) {
            Log.w("SoundManager", "Background asset not found: $assetName")
        }
    }

    fun playBackground(assetName: String) {
        try {
            stopBackground()
            val afd = context.assets.openFd(assetName)
            bgPlayer = MediaPlayer()
            bgPlayer?.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            bgPlayer?.isLooping = true
            bgPlayer?.prepare()
            bgPlayer?.start()
        } catch (e: Exception) {
            Log.w("SoundManager", "failed to play background: $assetName", e)
        }
    }

    fun pauseBackground() {
        bgPlayer?.pause()
    }

    fun stopBackground() {
        try {
            bgPlayer?.stop()
            bgPlayer?.release()
        } catch (e: Exception) {
        }
        bgPlayer = null
    }

    fun loadSfx(key: String, assetName: String) {
        try {
            val afd = context.assets.openFd(assetName)
            val id = soundPool.load(afd, 1)
            sounds[key] = id
        } catch (e: Exception) {
            // failed to load; ignore
        }
    }

    fun playSfx(key: String) {
        val id = sounds[key] ?: return
        soundPool.play(id, 1f, 1f, 1, 0, 1f)
    }

    fun release() {
        try { soundPool.release() } catch (e: Exception) {}
        stopBackground()
    }
}
