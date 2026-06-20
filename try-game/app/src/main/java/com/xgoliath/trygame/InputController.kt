package com.xgoliath.trygame

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent

class InputController {
    var left = false
    var right = false
    var jump = false
    var attack = false
    var ride = false

    // simple touch areas: left half bottom = left/right, right bottom quarter = jump, right-top small area = attack, ride toggle double tap?
    fun handleTouch(event: MotionEvent, width: Int, height: Int) {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                // left area
                left = x < width / 3
                right = x >= width / 3 && x < width * 2 / 3
                jump = x >= width * 2 / 3 && y > height / 2
                attack = x >= width * 2 / 3 && y <= height / 2
            }
            MotionEvent.ACTION_UP -> {
                left = false; right = false; jump = false; attack = false
            }
        }
    }

    fun drawDebug(canvas: Canvas, w: Int, h: Int) {
        val p = Paint()
        p.color = Color.argb(80, 0, 0, 0)
        if (left) canvas.drawRect(0f, h/2f, w/3f, h.toFloat(), p)
        if (right) canvas.drawRect(w/3f, h/2f, w*2/3f, h.toFloat(), p)
        if (jump) canvas.drawRect(w*2/3f, h/2f, w.toFloat(), h.toFloat(), p)
        if (attack) canvas.drawRect(w*2/3f, 0f, w.toFloat(), h/2f, p)
    }
}
