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
    fun setClaBackground(
        type: ClaRoundStateType = ClaRoundStateType.NORMAL,
        builder: ClaRoundViewBuilder.() -> Unit
    ) {
        helper.builderMap[type]?.let { b ->
            builder(b)
            helper.setRoundAndColor(claView)
        }
    }

    /**
     * 添加自定义的类型，但是请注意，这个类型只会替换normal状态下的设置
     */
    fun addClaCustomBg(key: Any, builder: ClaRoundViewBuilder.() -> Unit) {
        helper.addCustom(key, builder)
    }

    /**
     * 根据之前设置的key，显示自定义的背景设置
     * 这个设置只会在normal状态下生效，如果当前不是normal状态，那么这个方法会改变normal状态的背景，等到控件恢复到normal状态时，就显示的最后一次设置的背景
     * @param key 当key为null，表示恢复到normal默认的背景设置
     */
    fun setClaCustomBg(key: Any?) {
        helper.setCustom(key, claView)
    }

    fun getView(): View

    fun getClaRoundViewHelper(): ClaRoundViewHelper
}