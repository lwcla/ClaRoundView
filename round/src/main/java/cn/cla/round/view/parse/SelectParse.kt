package cn.cla.round.view.parse

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import cn.cla.round.view.R
import cn.cla.round.view.entity.ClaRoundViewBuilder
import cn.cla.round.view.utils.INVALID_VALUE
import cn.cla.round.view.utils.INVALID_VALUE_F

internal class SelectParse(
    context: Context,
    attr: AttributeSet?
) : ClaDrawableAbs(context, attr, R.styleable.ClaRoundSelectDrawable) {

    override fun TypedArray.parse(): ClaRoundViewBuilder {
        val bgColor =
            getColor(R.styleable.ClaRoundSelectDrawable_cla_select_backgroundColor, INVALID_VALUE)
        val bgColorAlpha =
            getFloat(R.styleable.ClaRoundSelectDrawable_cla_select_backgroundColorAlpha, 1f)

        val borderWidth =
            getDimension(R.styleable.ClaRoundSelectDrawable_cla_select_borderWidth, INVALID_VALUE_F)
        val borderDashWidth =
            getDimension(R.styleable.ClaRoundSelectDrawable_cla_select_borderDashWidth, 0f)
        val borderDashGap =
            getDimension(R.styleable.ClaRoundSelectDrawable_cla_select_borderDashGap, borderDashWidth)
        val borderColor =
            getColor(R.styleable.ClaRoundSelectDrawable_cla_select_borderColor, INVALID_VALUE)
        val borderColorAlpha =
            getFloat(R.styleable.ClaRoundSelectDrawable_cla_select_borderColorAlpha, INVALID_VALUE_F)

        val topLeftRadius =
            getDimension(R.styleable.ClaRoundSelectDrawable_cla_select_topLeftRadius, INVALID_VALUE_F)
        val topRightRadius =
            getDimension(R.styleable.ClaRoundSelectDrawable_cla_select_topRightRadius, INVALID_VALUE_F)
        val bottomLeftRadius =
            getDimension(R.styleable.ClaRoundSelectDrawable_cla_select_bottomLeftRadius, INVALID_VALUE_F)
        val bottomRightRadius =
            getDimension(R.styleable.ClaRoundSelectDrawable_cla_select_bottomRightRadius, INVALID_VALUE_F)
        val radius =
            getDimension(R.styleable.ClaRoundSelectDrawable_cla_select_radius, INVALID_VALUE_F)

        val radiusAdjustBounds =
            getBoolean(R.styleable.ClaRoundSelectDrawable_cla_select_radiusAdjustBounds, false)

        val textColor =
            getColor(R.styleable.ClaRoundSelectDrawable_cla_select_textColor, INVALID_VALUE)
        val textColorAlpha =
            getFloat(R.styleable.ClaRoundSelectDrawable_cla_select_textColorAlpha, INVALID_VALUE_F)

        val drawable = getDrawable(R.styleable.ClaRoundSelectDrawable_cla_select_drawable)

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
        )
    }
}