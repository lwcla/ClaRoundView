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
 * @property bgColorAlpha 背景颜色的透明度 0-1
 * @property borderWidth 边框宽度
 * @property borderColor 边框颜色
 * @property borderColorAlpha 边框颜色透明度 0-1
 * @property radiusAdjustBounds 设置圆角大小是否自动适应为 View 的高度的一半
 * @property dashWidth 边框虚线的长度
 * @property dashGap 边框虚线的间隔距离
 * @property topLeftRadius 左上的圆角，同时设置了[radius]时，topLeft会应用[topLeftRadius]
 * @property topRightRadius 右上的圆角，同时设置了[radius]时，topRight会应用[topRightRadius]
 * @property bottomLeftRadius 左下的圆角，同时设置了[radius]时，bottomLeft会应用[bottomLeftRadius]
 * @property bottomRightRadius 右下的圆角，同时设置了[radius]时，bottomRight会应用[bottomRightRadius]
 * @property radius 整体的圆角
 * @property drawable 设置背景图，这个优先级最高
 * @property textColor 文字的颜色
 * @property textColorAlpha 文字颜色戴尔透明度 0-1
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
    @ColorInt var textColor: Int = INVALID_VALUE,
    var textColorAlpha: Float = INVALID_VALUE_F,
) {

    private val roundDrawable by lazyNull { ClaRoundDrawable(this) }

    internal val hasDrawable get() = drawable != null || hasBorder || bgColor != INVALID_VALUE

    internal val readDrawable get() = drawable ?: roundDrawable.also { it.setRoundAndColor() }

    internal val hasRadius get() = hasAllRadius || hasSingleRadius

    internal val hasAllRadius get() = radius != INVALID_VALUE_F

    internal val hasSingleRadius get() = topLeftRadius != INVALID_VALUE_F || topRightRadius != INVALID_VALUE_F || bottomLeftRadius != INVALID_VALUE_F || bottomRightRadius != INVALID_VALUE_F

    internal val radii
        get() = floatArrayOf(
            singleRadius(topLeftRadius), singleRadius(topLeftRadius),
            singleRadius(topRightRadius), singleRadius(topRightRadius),
            singleRadius(bottomRightRadius), singleRadius(bottomRightRadius),
            singleRadius(bottomLeftRadius), singleRadius(bottomLeftRadius),
        )

    internal val radiusAdjust get() = !hasRadius && radiusAdjustBounds

    internal val hasBorder get() = borderWidth >= 0 && borderColor != INVALID_VALUE

    internal val hasBgColor get() = bgColor != INVALID_VALUE

    internal val aBgColor get() = bgColor.changeColorAlpha(bgColorAlpha)

    internal val aBorderColor get() = borderColor.changeColorAlpha(borderColorAlpha)

    internal val aTextColor get() = textColor.changeColorAlpha(textColorAlpha)

    internal val hasTextColor get() = textColor != INVALID_VALUE

    /**
     * 获取单边的圆角，如果单边圆角没有设置的花，那就取整体的圆角值
     * @param singleRadius 单边的圆角
     * @return Float
     */
    private fun singleRadius(singleRadius: Float): Float {
        if (singleRadius != INVALID_VALUE_F) {
            return singleRadius
        }

        return radius
    }
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