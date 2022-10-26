package cn.cla.round.view.parse

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import cn.cla.round.view.R
import cn.cla.round.view.entity.ClaRoundViewBuilder
import cn.cla.round.view.textBold
import cn.cla.round.view.textItalic
import cn.cla.round.view.textNormal
import cn.cla.round.view.utils.INVALID_VALUE
import cn.cla.round.view.utils.INVALID_VALUE_F

internal class PressParse(
    context: Context,
    attr: AttributeSet?
) : ClaDrawableAbs(context, attr, R.styleable.ClaRoundPressDrawable) {

    override fun TypedArray.parse(): ClaRoundViewBuilder {
        val bgColor =
            getColor(R.styleable.ClaRoundPressDrawable_cla_press_backgroundColor, INVALID_VALUE)
        val bgColorAlpha =
            getFloat(R.styleable.ClaRoundPressDrawable_cla_press_backgroundColorAlpha, 1f)

        val borderWidth =
            getDimension(R.styleable.ClaRoundPressDrawable_cla_press_borderWidth, INVALID_VALUE_F)
        val borderDashWidth =
            getDimension(R.styleable.ClaRoundPressDrawable_cla_press_borderDashWidth, 0f)
        val borderDashGap =
            getDimension(R.styleable.ClaRoundPressDrawable_cla_press_borderDashGap, borderDashWidth)
        val borderColor =
            getColor(R.styleable.ClaRoundPressDrawable_cla_press_borderColor, INVALID_VALUE)
        val borderColorAlpha =
            getFloat(R.styleable.ClaRoundPressDrawable_cla_press_borderColorAlpha, INVALID_VALUE_F)

        val topLeftRadius =
            getDimension(R.styleable.ClaRoundPressDrawable_cla_press_topLeftRadius, INVALID_VALUE_F)
        val topRightRadius =
            getDimension(R.styleable.ClaRoundPressDrawable_cla_press_topRightRadius, INVALID_VALUE_F)
        val bottomLeftRadius =
            getDimension(R.styleable.ClaRoundPressDrawable_cla_press_bottomLeftRadius, INVALID_VALUE_F)
        val bottomRightRadius =
            getDimension(R.styleable.ClaRoundPressDrawable_cla_press_bottomRightRadius, INVALID_VALUE_F)
        val radius =
            getDimension(R.styleable.ClaRoundPressDrawable_cla_press_radius, INVALID_VALUE_F)

        val radiusAdjustBounds =
            getBoolean(R.styleable.ClaRoundPressDrawable_cla_press_radiusAdjustBounds, false)

        val textColor =
            getColor(R.styleable.ClaRoundPressDrawable_cla_press_textColor, INVALID_VALUE)
        val textColorAlpha =
            getFloat(R.styleable.ClaRoundPressDrawable_cla_press_textColorAlpha, INVALID_VALUE_F)

        val drawable = getDrawable(R.styleable.ClaRoundPressDrawable_cla_press_drawable)

        val textTypeface = getInt(R.styleable.ClaRoundPressDrawable_cla_press_textStyle, 0)

        return ClaRoundViewBuilder(
            drawable = drawable,
            bgColor = bgColor,
            bgColorAlpha = bgColorAlpha,
            borderWidth = borderWidth,
            borderColor = borderColor,
            borderColorAlpha = borderColorAlpha,
            radiusAdjustBounds = radiusAdjustBounds,
            topLeftRadius = topLeftRadius,
            topRightRadius = topRightRadius,
            bottomLeftRadius = bottomLeftRadius,
            bottomRightRadius = bottomRightRadius,
            radius = radius,
            textColor = textColor,
            textColorAlpha = textColorAlpha,
            dashWidth = borderDashWidth,
            dashGap = borderDashGap,
            textTypeface = when (textTypeface) {
                0 -> textNormal
                1 -> textBold
                else -> textItalic
            },
        )
    }
}