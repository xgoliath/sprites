package com.xgoliath.amamagame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.WindowManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Keep screen on
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        
        // Set game view
        setContentView(GameView(this))
    }
}
