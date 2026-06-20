package com.xgoliath.trygame

object Config {
    // baseline character height in pixels for a medium-density device. Sprites will be scaled to this height.
    // You can tweak this value to make characters larger/smaller globally.
    const val TARGET_CHARACTER_HEIGHT = 128

    // companion size relative to main character
    const val COMPANION_SCALE = 0.75f

    // enemy size relative to main character
    const val ENEMY_SCALE = 0.9f

    // virtual baseline resolution (width x height) for layout/scaling decisions
    const val VIRTUAL_WIDTH = 1280
    const val VIRTUAL_HEIGHT = 720
}
