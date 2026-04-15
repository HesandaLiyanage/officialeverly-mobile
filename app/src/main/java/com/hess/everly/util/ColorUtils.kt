package com.hess.everly.util

import android.graphics.Color

fun String.toColorInt(fallback: String = "#E5E7EB"): Int = try {
    Color.parseColor(this)
} catch (_: IllegalArgumentException) {
    Color.parseColor(fallback)
}

