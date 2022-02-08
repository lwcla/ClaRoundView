package cn.cla.round.view.inf

import android.content.Context
import android.util.AttributeSet
import android.view.View
import cn.cla.round.view.R

internal interface ClaAlphaViewInf {

    private val claView get() = getView()

    private val normalAlpha: Float
        get() = claView.getTag(R.id.cla_view_normal_alpha) as? Float? ?: 1f

    var pressedAlpha: Float
        get() = claView.getTag(R.id.cla_view_press_alpha) as? Float? ?: 0.5f
        set(value) = claView.setTag(R.id.cla_view_press_alpha, value)

    var disabledAlpha: Float
        get() = claView.getTag(R.id.cla_view_disable_alpha) as? Float? ?: 0.5f
        set(value) = claView.setTag(R.id.cla_view_disable_alpha, value)

    var changeAlphaWhenPress: Boolean
        get() = claView.getTag(R.id.cla_view_change_alpha_when_press) as? Boolean? ?: false
        set(value) = claView.setTag(R.id.cla_view_change_alpha_when_press, value)

    var changeAlphaWhenDisable: Boolean
        get() = claView.getTag(R.id.cla_view_change_alpha_when_disable) as? Boolean? ?: false
        set(value) = claView.setTag(R.id.cla_view_change_alpha_when_disable, value)

    fun initAlphaChange(context: Context, attr: AttributeSet?) {
        val array = context.obtainStyledAttributes(attr, R.styleable.ClaViewChangeAlpha)

        val pAlpha = array.getFloat(R.styleable.ClaViewChangeAlpha_cla_view_pressAlpha, -1f)
        val dAlpha = array.getFloat(R.styleable.ClaViewChangeAlpha_cla_view_disableAlpha, -1f)
        val press =
            array.getBoolean(R.styleable.ClaViewChangeAlpha_cla_view_changeAlpha_whenPress, false)
        val disable =
            array.getBoolean(R.styleable.ClaViewChangeAlpha_cla_view_changeAlpha_whenDisable, false)

        if (pAlpha > 0) {
            pressedAlpha = minOf(pAlpha, 1f)
        }

        if (dAlpha > 0) {
            disabledAlpha = minOf(dAlpha, 1f)
        }

        if (press != changeAlphaWhenPress) {
            changeAlphaWhenPress = press
        }

        if (disable != changeAlphaWhenDisable) {
            changeAlphaWhenDisable = disable
        }

        if (changeAlphaWhenPress) {
            claView.isFocusable = true
            claView.isClickable = true
        }

        array.recycle()
    }

    fun onPressedChanged(current: View, pressed: Boolean) {
        claView.apply {
            if (current.isEnabled) {
                alpha = if (changeAlphaWhenPress && pressed && current.isClickable) {
                    pressedAlpha
                } else {
                    normalAlpha
                }
            } else if (changeAlphaWhenDisable) {
                alpha = disabledAlpha
            }
        }
    }

    fun onEnabledChanged(current: View, enabled: Boolean) {
        claView.apply {
            val alphaForIsEnable = if (changeAlphaWhenDisable) {
                if (enabled) normalAlpha else disabledAlpha
            } else {
                normalAlpha
            }
            if (current != this && isEnabled != enabled) {
                isEnabled = enabled
            }
            alpha = alphaForIsEnable
        }
    }

    fun getView(): View
}