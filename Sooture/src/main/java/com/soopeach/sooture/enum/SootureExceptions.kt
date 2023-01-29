package com.soopeach.sooture.enum

/**
 *
 * @author soopeach
 * @since 1.0.0
 *
 */
internal enum class SootureExceptions(val exception: Exception) {
    INVALID_VIEW(Exception("Invalid View")),
    INVALID_DATE(Exception("Invalid Date"))
}


