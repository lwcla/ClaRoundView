package cn.cla.round.view.parse

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import cn.cla.round.view.R
import cn.cla.round.view.drawable.ClaDrawableAc
import cn.cla.round.view.entity.ClaRoundViewBuilder
import cn.cla.round.view.utils.INVALID_VALUE
import cn.cla.round.view.utils.INVALID_VALUE_F

internal class NormalParse(
    context: Context,
    attr: AttributeSet?
) : ClaDrawableAc(context, attr, R.styleable.ClaRoundNormalDrawable) {

    override fun TypedArray.parse(): ClaRoundViewBuilder {
        val bgColor =
            getColor(R.styleable.ClaRoundNormalDrawable_cla_normal_backgroundColor, INVALID_VALUE)
        val bgColorAlpha =
            getFloat(R.styleable.ClaRoundNormalDrawable_cla_normal_backgroundColorAlpha, 1f)

        val borderWidth =
            getDimension(R.styleable.ClaRoundNormalDrawable_cla_normal_borderWidth, INVALID_VALUE_F)
        val borderDashWidth =
            getDimension(R.styleable.ClaRoundNormalDrawable_cla_normal_borderDashWidth, 0f)
        val borderDashGap =
            getDimension(R.styleable.ClaRoundNormalDrawable_cla_normal_borderDashGap, borderDashWidth)
        val borderColor =
            getColor(R.styleable.ClaRoundNormalDrawable_cla_normal_borderColor, INVALID_VALUE)
        val borderColorAlpha =
            getFloat(R.styleable.ClaRoundNormalDrawable_cla_normal_borderColorAlpha, INVALID_VALUE_F)

        val topLeftRadius =
            getDimension(R.styleable.ClaRoundNormalDrawable_cla_normal_topLeftRadius, INVALID_VALUE_F)
        val topRightRadius =
            getDimension(R.styleable.ClaRoundNormalDrawable_cla_normal_topRightRadius, INVALID_VALUE_F)
        val bottomLeftRadius =
            getDimension(R.styleable.ClaRoundNormalDrawable_cla_normal_bottomLeftRadius, INVALID_VALUE_F)
        val bottomRightRadius =
            getDimension(R.styleable.ClaRoundNormalDrawable_cla_normal_bottomRightRadius, INVALID_VALUE_F)
        val radius =
            getDimension(R.styleable.ClaRoundNormalDrawable_cla_normal_radius, INVALID_VALUE_F)

        val radiusAdjustBounds =
            getBoolean(R.styleable.ClaRoundNormalDrawable_cla_normal_radiusAdjustBounds, false)

        val textColor =
            getColor(R.styleable.ClaRoundNormalDrawable_cla_normal_textColor, INVALID_VALUE)
        val textColorAlpha =
            getFloat(R.styleable.ClaRoundNormalDrawable_cla_normal_textColorAlpha, INVALID_VALUE_F)

        val drawable =
            getDrawable(R.styleable.ClaRoundNormalDrawable_cla_normal_drawable)

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