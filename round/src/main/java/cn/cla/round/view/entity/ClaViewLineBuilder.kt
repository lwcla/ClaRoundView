package cn.cla.round.view.entity

import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import androidx.annotation.ColorInt
import cn.cla.round.view.utils.INVALID_VALUE
import cn.cla.round.view.utils.INVALID_VALUE_F
import cn.cla.round.view.utils.changeColorAlpha

/**
 * 控件四周的线条
 * @property lineWidth 线条宽度
 * @property lineDashWidth 虚线长度
 * @property lineDashGap 虚线间隔
 * @property lineColor 线条颜色
 * @property lineColorAlpha 线条的透明度 0-1
 * @property lineSpace 线条和上下左右的间隔
 * @property showTop 是否显示顶部
 * @property showLeft 是否显示左边
 * @property showRight 是否显示右边
 * @property showBottom 是否显示底部
 * @property lineTopSpace 线条距顶部的距离
 * @property lineBottomSpace 线条距底部的距离
 * @property lineLeftSpace 线条距左边的距离
 * @property lineRightSpace 线条距右边的距离
 * @constructor
 */
data class ClaViewLineBuilder(
    var lineWidth: Float = 0f,
    var lineDashWidth: Float = 0f,
    var lineDashGap: Float = 0f,
    @ColorInt var lineColor: Int = INVALID_VALUE,
    var lineColorAlpha: Float = INVALID_VALUE_F,
    var lineSpace: Float = 0f,
    var showTop: Boolean = false,
    var showLeft: Boolean = false,
    var showRight: Boolean = false,
    var showBottom: Boolean = false,
    var lineTopSpace: Float = 0f,
    var lineBottomSpace: Float = 0f,
    var lineLeftSpace: Float = 0f,
    var lineRightSpace: Float = 0f,
    internal var paint2: Paint? = null,
    internal val path: Path = Path(),
    internal var pathEffect2: DashPathEffect? = null
) {

    internal val lineRealColor get() = lineColor.changeColorAlpha(lineColorAlpha)

    internal val showLine get() = showTop || showBottom || showLeft || showRight

    internal val hasLine get() = lineWidth != 0f && lineRealColor != INVALID_VALUE

    internal val topSpace get() = if (lineTopSpace > 0) lineTopSpace else lineSpace
    internal val bottomSpace get() = if (lineBottomSpace > 0) lineBottomSpace else lineSpace
    internal val leftSpace get() = if (lineLeftSpace > 0) lineLeftSpace else lineSpace
    internal val rightSpace get() = if (lineRightSpace > 0) lineRightSpace else lineSpace

    internal val paint: Paint
        get() {
            var p = paint2
            if (p == null) {
                p = Paint().also {
                    kotlin.runCatching {
                        it.isAntiAlias = true
                        val color = lineRealColor
                        it.color = if (color == INVALID_VALUE) Color.TRANSPARENT else color
                        it.strokeWidth = lineWidth

                        if (lineDashWidth <= 0f) {
//                            it.pathEffect = pathEffect
                            it.style = Paint.Style.FILL_AND_STROKE
                        } else {
                            it.pathEffect = pathEffect
                            it.style = Paint.Style.STROKE
                        }
                    }
                }
                paint2 = p
            }

            return p
        }

    internal val pathEffect: DashPathEffect
        get() {
            var p = pathEffect2
            if (p == null) {
                //意思是所画虚线规则是先画10个长度的实线，留下5个长度的空白
                p = DashPathEffect(floatArrayOf(lineDashWidth, lineDashGap), 0f)
                pathEffect2 = p
            }

            return p
        }

    fun reset() {
        pathEffect2 = null
        paint2 = null
    }
}