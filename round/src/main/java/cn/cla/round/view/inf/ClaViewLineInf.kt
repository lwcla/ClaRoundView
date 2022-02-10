package cn.cla.round.view.inf

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import cn.cla.round.view.R
import cn.cla.round.view.entity.ClaViewLineBuilder
import cn.cla.round.view.utils.INVALID_VALUE
import cn.cla.round.view.utils.INVALID_VALUE_F

interface ClaViewLineInf {

    private val claView get() = getView()

    private var builder: ClaViewLineBuilder
        get() = claView.getTag(R.id.cla_view_line_builder) as? ClaViewLineBuilder? ?: ClaViewLineBuilder()
        set(value) = claView.setTag(R.id.cla_view_line_builder, value)

    private val viewWidth get() = claView.width.toFloat()
    private val viewHeight get() = claView.height.toFloat()

    private val lineEndX get() = viewWidth - builder.rightSpace
    private val lineEndY get() = viewHeight - builder.bottomSpace

    fun initLine(context: Context, attr: AttributeSet?) {
        val array = context.obtainStyledAttributes(attr, R.styleable.ClaViewLine)

        val lineWidth = array.getDimension(R.styleable.ClaViewLine_cla_view_lineWidth, 0f)
        val lineDashWidth = array.getDimension(R.styleable.ClaViewLine_cla_view_lineDashWidth, 0f)
        val lineDashGap =
            array.getDimension(R.styleable.ClaViewLine_cla_view_lineDashGap, lineDashWidth)

        val lineColor = array.getColor(R.styleable.ClaViewLine_cla_view_lineColor, INVALID_VALUE)
        val lineColorAlpha =
            array.getFloat(R.styleable.ClaViewLine_cla_view_lineColorAlpha, INVALID_VALUE_F)
        val lineSpace = array.getDimension(R.styleable.ClaViewLine_cla_view_lineSpace, 0f)
        val showTop = array.getBoolean(R.styleable.ClaViewLine_cla_view_showTopLine, false)
        val showLeft = array.getBoolean(R.styleable.ClaViewLine_cla_view_showLeftLine, false)
        val showRight = array.getBoolean(R.styleable.ClaViewLine_cla_view_showRightLine, false)
        val showBottom = array.getBoolean(R.styleable.ClaViewLine_cla_view_showBottomLine, false)

        val lineTopSpace =
            array.getDimension(R.styleable.ClaViewLine_cla_view_lineTopSpace, 0f)
        val lineBottomSpace =
            array.getDimension(R.styleable.ClaViewLine_cla_view_lineBottomSpace, 0f)
        val lineLeftSpace =
            array.getDimension(R.styleable.ClaViewLine_cla_view_lineLeftSpace, 0f)
        val lineRightSpace =
            array.getDimension(R.styleable.ClaViewLine_cla_view_lineRightSpace, 0f)

        array.recycle()

        builder = ClaViewLineBuilder(
            lineWidth,
            lineDashWidth,
            lineDashGap,
            lineColor,
            lineColorAlpha,
            lineSpace,
            showTop,
            showLeft,
            showRight,
            showBottom,
            lineTopSpace,
            lineBottomSpace,
            lineLeftSpace,
            lineRightSpace
        )
    }

    fun drawLine(canvas: Canvas?) {

        if (canvas == null) {
            return
        }

        builder.apply {

            if (!showLine || !hasLine) {
                return
            }

            kotlin.runCatching {
                //画4个边
                if (showTop) {
                    path.reset()
                    path.moveTo(leftSpace, 0f)
                    path.lineTo(lineEndX, 0f)
                    canvas.drawPath(path, paint)
                }

                if (showBottom) {
                    path.reset()
                    path.moveTo(leftSpace, viewHeight)
                    path.lineTo(lineEndX, viewHeight)
                    canvas.drawPath(path, paint)
                }

                if (showLeft) {
                    path.reset()
                    path.moveTo(0f, topSpace)
                    path.lineTo(0f, lineEndY)
                    canvas.drawPath(path, paint)
                }

                if (showRight) {
                    path.reset()
                    path.moveTo(viewWidth, topSpace)
                    path.lineTo(viewWidth, lineEndY)
                    canvas.drawPath(path, paint)
                }
            }
        }
    }

    fun getView(): View

    fun resetClaLine(block: ClaViewLineBuilder.() -> Unit) {
        val b = builder
        b.reset()
        block(b)
        getView().postInvalidate()
    }
}