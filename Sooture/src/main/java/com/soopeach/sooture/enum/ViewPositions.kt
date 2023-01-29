package com.soopeach.sooture.enum

/**
 *
 * @author soopeach
 * @since 1.0.0
 * @param (baseCanvasWidth, targetCanvasWidth), (baseCanvasHeight, targetCanvasHeight)
 * @return left and top offset for targetCanvas to be drawn
 *
 */
enum class ViewPositions(val left: (Int, Int) -> Float, val top: (Int, Int) -> Float) {
    TOP(
        left = { base, target ->
            (base / 2 - target / 2).toFloat()
        }, top = { _, _ ->
            0f
        }
    ),
    CENTER(
        left = { base, target ->
            (base / 2 - target / 2).toFloat()
        }, top = { base, target ->
            (base / 2 - target / 2).toFloat()
        }
    ),
    BOTTOM(
        left = { base, target ->
            (base / 2 - target / 2).toFloat()
        }, top = { base, target ->
            (base - target).toFloat()
        }
    )
}