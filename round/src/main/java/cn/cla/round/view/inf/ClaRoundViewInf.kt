package cn.cla.round.view.inf

import android.view.View
import cn.cla.round.view.ClaRoundViewHelper
import cn.cla.round.view.entity.ClaRoundViewBuilder


enum class ClaRoundStateType {
    /** 普通状态 */
    NORMAL,

    /** 按压状态 */
    PRESSED,

    /** 选中状态 */
    SELECTED,

    /** 获取焦点状态 */
    FOCUS,

    /** 激活状态 */
    ACTIVATED,

    /** disable状态 */
    DISABLE,
}

interface ClaRoundViewInf {

    private val helper get() = getClaRoundViewHelper()
    private val claView get() = getView()

    fun initRound() {
        helper.setRoundAndColor(claView)
    }

    /** 设置按钮的背景色 */
    fun setClaBackground(type: ClaRoundStateType = ClaRoundStateType.NORMAL, builder: ClaRoundViewBuilder.() -> Unit) {
        helper.builderMap[type]?.let { b ->
            builder(b)
            helper.setRoundAndColor(claView)
        }
    }

    fun getView(): View

    fun getClaRoundViewHelper(): ClaRoundViewHelper
}