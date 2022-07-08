package cn.cla.round.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import cn.cla.round.view.entity.ClaRoundViewBuilder
import cn.cla.round.view.entity.addToColorState
import cn.cla.round.view.entity.addToStateDrawable
import cn.cla.round.view.inf.ClaRoundStateType
import cn.cla.round.view.parse.*
import cn.cla.round.view.utils.lazyNull

class ClaRoundViewHelper(
    context: Context,
    attr: AttributeSet?
) {

    //获取对应的属性值 Android框架自带的属性 attr
    private val pressed = android.R.attr.state_pressed
    private val unpressed = -pressed
    private val focused = android.R.attr.state_focused
    private val unfocused = -focused
    private val select = android.R.attr.state_selected
    private val unselect = -select
    private val activated = android.R.attr.state_activated
    private val unactivated = -activated
    private val enable = android.R.attr.state_enabled
    private val disable = -enable

    private val normalBuilder by lazyNull { NormalParse(context, attr).builder }
    private val pressBuilder by lazyNull { PressParse(context, attr).builder }
    private val selectBuilder by lazyNull { SelectParse(context, attr).builder }
    private val focusBuilder by lazyNull { FocusParse(context, attr).builder }
    private val activatedBuilder by lazyNull { ActivatedParse(context, attr).builder }
    private val disableBuilder by lazyNull { DisableParse(context, attr).builder }

    val builderMap by lazyNull {
        mutableMapOf(
            ClaRoundStateType.NORMAL to normalBuilder,
            ClaRoundStateType.PRESSED to pressBuilder,
            ClaRoundStateType.SELECTED to selectBuilder,
            ClaRoundStateType.FOCUS to focusBuilder,
            ClaRoundStateType.ACTIVATED to activatedBuilder,
            ClaRoundStateType.DISABLE to disableBuilder,
        )
    }

    private val customMap by lazyNull { mutableMapOf<Any?, ClaRoundViewBuilder>() }
    private var curCustomBuilder: ClaRoundViewBuilder? = null

    fun setRoundAndColor(view: View) {
        setBackground(view)
        setTextColor(view)
    }

    fun addCustom(key: Any, builder: ClaRoundViewBuilder.() -> Unit) {
        val value = customMap[key] ?: ClaRoundViewBuilder()
        builder(value)
        customMap[key] = value
    }

    fun setCustom(key: Any?, view: View) {
        val builder = customMap[key]
        if (System.identityHashCode(builder) == System.identityHashCode(curCustomBuilder)) {
            //不需要去刷新view
            return
        }

        curCustomBuilder = builder
        setRoundAndColor(view)
        view.postInvalidate()
    }

    private fun setBackground(v: View) {
        val list = mutableListOf<Pair<IntArray, Drawable>>()

        disableBuilder.addToStateDrawable(list, disable)
        pressBuilder.addToStateDrawable(list, pressed)
        focusBuilder.addToStateDrawable(list, focused)
        activatedBuilder.addToStateDrawable(list, activated)
        selectBuilder.addToStateDrawable(list, select)
        val realNormalBuilder = curCustomBuilder ?: normalBuilder
        realNormalBuilder.addToStateDrawable(list, null)

        //避免没有设置这些属性的时候覆盖了background
        if (list.isEmpty()) {
            return
        }

        val stateDrawable = StateListDrawable()
        list.forEach { stateDrawable.addState(it.first, it.second) }
        v.background = stateDrawable
    }

    private fun setTextColor(v: View) {
        if (v !is TextView) {
            return
        }

        val stateList = mutableListOf<IntArray>()
        val colorList = mutableListOf<Int>()

        disableBuilder.addToColorState(stateList, colorList, disable)
        pressBuilder.addToColorState(stateList, colorList, pressed)
        focusBuilder.addToColorState(stateList, colorList, focused)
        activatedBuilder.addToColorState(stateList, colorList, activated)
        selectBuilder.addToColorState(stateList, colorList, select)
        val realNormalBuilder = curCustomBuilder ?: normalBuilder
        realNormalBuilder.addToColorState(stateList, colorList, null)

        //避免没有设置这些属性的时候覆盖了textColor
        if (stateList.isEmpty() || colorList.isEmpty()) {
            return
        }

        val color = ColorStateList(stateList.toTypedArray(), colorList.toIntArray())
        v.setTextColor(color)
    }
}