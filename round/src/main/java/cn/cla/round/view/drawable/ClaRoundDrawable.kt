package cn.cla.round.view.drawable

import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import cn.cla.round.view.entity.ClaRoundViewBuilder
import kotlin.math.roundToInt

internal class ClaRoundDrawable(
    private val builder: ClaRoundViewBuilder
) : GradientDrawable() {

    fun setRoundAndColor() {
        setColor(Color.TRANSPARENT)
        setStroke(0, Color.TRANSPARENT)

        if (builder.hasSingleRadius) {
            cornerRadii = null
        }

        if (builder.hasAllRadius) {
            cornerRadius = 0f
        }

        if (builder.radiusAdjust) {
            onBoundsChange(bounds)
        }
    }

    override fun setColor(argb: Int) {
        if (builder.hasBgColor) {
            super.setColor(builder.aBgColor)
            return
        }

        super.setColor(argb)
    }

    override fun setStroke(width: Int, color: Int) {
        if (builder.hasBorder) {
            super.setStroke(builder.borderWidth.roundToInt(), builder.aBorderColor, builder.dashWidth, builder.dashGap)
            return
        }

        super.setStroke(width, color)
    }

    override fun setCornerRadii(radii: FloatArray?) {
        if (builder.hasSingleRadius) {
            super.setCornerRadii(builder.radii)
            return
        }

        if (builder.hasAllRadius) {
            super.setCornerRadius(builder.radius)
            return
        }

        super.setCornerRadii(radii)
    }

    override fun setCornerRadius(radius: Float) {
        if (builder.hasSingleRadius) {
            super.setCornerRadii(builder.radii)
            return
        }

        if (builder.hasAllRadius) {
            super.setCornerRadius(builder.radius)
            return
        }

        super.setCornerRadius(radius)
    }

    override fun onBoundsChange(r: Rect) {
        super.onBoundsChange(r)
        if (builder.radiusAdjust) {
            // 修改圆角为短边的一半
            cornerRadius = (r.width().coerceAtMost(r.height()) / 2).toFloat()
        }
    }
}