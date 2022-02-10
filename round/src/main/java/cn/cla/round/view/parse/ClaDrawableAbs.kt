package cn.cla.round.view.parse

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.annotation.StyleableRes
import cn.cla.round.view.entity.ClaRoundViewBuilder
import cn.cla.round.view.utils.lazyNull

internal abstract class ClaDrawableAbs(
    context: Context,
    attr: AttributeSet?,
    @StyleableRes attrs: IntArray
) {

    val builder by lazyNull {
        context.obtainStyledAttributes(attr, attrs).run {
            val builder = parse()
            recycle()
            builder
        }
    }

    protected abstract fun TypedArray.parse(): ClaRoundViewBuilder

}