package cn.cla.round.view.entity

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import cn.cla.round.view.drawable.ClaRoundDrawable
import cn.cla.round.view.utils.INVALID_VALUE
import cn.cla.round.view.utils.INVALID_VALUE_F
import cn.cla.round.view.utils.changeColorAlpha
import cn.cla.round.view.utils.lazyNull


/**
 * 圆角图builder
 * @property bgColor 背景颜色
 * @property bgColorAlpha 背景颜色的透明度
 * @property borderWidth 边框宽度
 * @property borderColor 边框颜色
 * @property borderColorAlpha 边框颜色透明度
 * @property radiusAdjustBounds 设置圆角大小是否自动适应为 View 的高度的一半
 * @property dashWidth 边框虚线的长度
 * @property dashGap 边框虚线的间隔距离
 * @property topLeftRadius
 * @property topRightRadius
 * @property bottomLeftRadius
 * @property bottomRightRadius
 * @property radius
 * @property drawable 设置背景图，这个优先级最高
 * @constructor
 */
data class ClaRoundViewBuilder(
    var drawable: Drawable? = null,
    @ColorInt var bgColor: Int = INVALID_VALUE,
    var bgColorAlpha: Float = 1f,
    var borderWidth: Float = 0f,
    var dashWidth: Float = 0f,
    var dashGap: Float = 0f,
    @ColorInt var borderColor: Int = INVALID_VALUE,
    var borderColorAlpha: Float = 1f,
    var radiusAdjustBounds: Boolean = false,
    var topLeftRadius: Float = INVALID_VALUE_F,
    var topRightRadius: Float = INVALID_VALUE_F,
    var bottomLeftRadius: Float = INVALID_VALUE_F,
    var bottomRightRadius: Float = INVALID_VALUE_F,
    var radius: Float = INVALID_VALUE_F,
    var textColor: Int = INVALID_VALUE,
    var textColorAlpha: Float = INVALID_VALUE_F,
) {

    private val roundDrawable by lazyNull { ClaRoundDrawable(this) }

    val hasDrawable get() = drawable != null || hasBorder || bgColor != INVALID_VALUE

    val readDrawable get() = drawable ?: roundDrawable.also { it.setRoundAndColor() }

    val hasRadius get() = hasAllRadius || hasSingleRadius

    val hasAllRadius get() = radius != INVALID_VALUE_F

    val hasSingleRadius get() = !hasAllRadius && (topLeftRadius != INVALID_VALUE_F || topRightRadius != INVALID_VALUE_F || bottomLeftRadius != INVALID_VALUE_F || bottomRightRadius != INVALID_VALUE_F)

    val radii
        get() = floatArrayOf(
            topLeftRadius, topLeftRadius,
            topRightRadius, topRightRadius,
            bottomRightRadius, bottomRightRadius,
            bottomLeftRadius, bottomLeftRadius,
        )

    val radiusAdjust get() = !hasRadius && radiusAdjustBounds

    val hasBorder get() = borderWidth >= 0 && borderColor != INVALID_VALUE

    val hasBgColor get() = bgColor != INVALID_VALUE

    val aBgColor get() = bgColor.changeColorAlpha(bgColorAlpha)

    val aBorderColor get() = borderColor.changeColorAlpha(borderColorAlpha)

    val aTextColor get() = textColor.changeColorAlpha(textColorAlpha)

    val hasTextColor get() = textColor != INVALID_VALUE
}

internal fun ClaRoundViewBuilder.addToStateDrawable(list: MutableList<Pair<IntArray, Drawable>>, state: Int?) {
    if (!hasDrawable) {
        return
    }

    val stateArray = if (state == null) {
        intArrayOf()
    } else {
        intArrayOf(state)
    }

    list.add(stateArray to readDrawable)
}

internal fun ClaRoundViewBuilder.addToColorState(
    stateList: MutableList<IntArray>,
    colorList: MutableList<Int>,
    state: Int?
) {
    if (!hasTextColor) {
        return
    }

    val stateArray = if (state == null) {
        intArrayOf()
    } else {
        intArrayOf(state)
    }

    stateList.add(stateArray)
    colorList.add(aTextColor)
}