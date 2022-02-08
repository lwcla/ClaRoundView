package cn.cla.round.view.utils

import android.graphics.Color
import kotlin.math.abs
import kotlin.math.min

internal const val INVALID_VALUE = -1000
internal const val INVALID_VALUE_F = INVALID_VALUE.toFloat()

internal fun Int.changeColorAlpha(percent: Float) = this.run {

    if (percent == INVALID_VALUE_F) {
        return@run this
    }

    kotlin.runCatching {
        val r = Color.red(this)
        val g = Color.green(this)
        val b = Color.blue(this)
        val a = Color.alpha(this)
        val alpha = min(abs((a * percent).toInt()), 255)
        Color.argb(alpha, r, g, b)
    }.getOrElse {
        this
    }
}

/**
 * 不加锁的lazy
 * 加锁太多会影响性能
 */
internal inline fun <T> lazyNull(crossinline block: () -> T) = lazy(LazyThreadSafetyMode.NONE) { block() }