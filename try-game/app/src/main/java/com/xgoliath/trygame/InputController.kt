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
    // rideRequested is set when a multi-touch (two+ fingers) DOWN occurs; the game toggles ride once per request
    var rideRequested = false

    // simple touch areas: left third = left, middle third = right, right-bottom half = jump, right-top half = attack
    fun handleTouch(event: MotionEvent, width: Int, height: Int) {
        val x = event.x
        val y = event.y
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN, MotionEvent.ACTION_MOVE -> {
                // detect multi-touch for ride toggle
                if (event.pointerCount > 1 && (event.actionMasked == MotionEvent.ACTION_DOWN || event.actionMasked == MotionEvent.ACTION_POINTER_DOWN)) {
                    rideRequested = true
                }

                left = x < width / 3f
                right = x >= width / 3f && x < width * 2 / 3f
                jump = x >= width * 2 / 3f && y > height / 2f
                attack = x >= width * 2 / 3f && y <= height / 2f
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                // if all pointers are up, clear controls for that finger set
                if (event.pointerCount <= 1) {
                    left = false; right = false; jump = false; attack = false
                }
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
