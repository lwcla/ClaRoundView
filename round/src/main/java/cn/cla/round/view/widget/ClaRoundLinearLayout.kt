package cn.cla.round.view.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import cn.cla.round.view.ClaRoundViewHelper
import cn.cla.round.view.inf.ClaAlphaViewInf
import cn.cla.round.view.inf.ClaRoundViewInf
import cn.cla.round.view.inf.ClaViewLineInf
import cn.cla.round.view.utils.lazyNull

open class ClaRoundLinearLayout(
    context: Context,
    attr: AttributeSet? = null
) : LinearLayoutCompat(context, attr), ClaRoundViewInf, ClaAlphaViewInf, ClaViewLineInf {

    private val roundHelper by lazyNull { ClaRoundViewHelper(context, attr) }

    init {
        initAlphaChange(context, attr)
        initRound()
        initLine(context, attr)
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        drawLine(canvas)
    }

    override fun setPressed(pressed: Boolean) {
        super.setPressed(pressed)
        onPressedChanged(this, pressed)
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        onEnabledChanged(this, enabled)
    }

    override fun getView(): View = this

    override fun getClaRoundViewHelper(): ClaRoundViewHelper = roundHelper

}