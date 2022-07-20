/*
 * Tencent is pleased to support the open source community by making QMUI_Android available.
 *
 * Copyright (C) 2017-2018 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the MIT License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.cla.round.view.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import cn.cla.round.view.inf.ClaAlphaViewInf
import cn.cla.round.view.inf.ClaShadowLayoutInf

/**
 * @author 阴影 复制的qmui的代码
 * 配置取消一个边的圆角，阴影会丢失，阴影颜色只能通过代码修改
 */
open class ClaShadowConstraintLayout(context: Context, attr: AttributeSet? = null) : ConstraintLayout(context, attr), ClaAlphaViewInf, ClaShadowLayoutInf {

    private val layoutHelper = ClaShadowLayoutHelper(context, attr, 0, this)

    init {
        initAlphaChange(context, attr)
    }

    override fun setPressed(pressed: Boolean) {
        super.setPressed(pressed)
        onPressedChanged(this, pressed)
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        onEnabledChanged(this, enabled)
    }


    override fun dispatchDraw(canvas: Canvas) {
        try {
            super.dispatchDraw(canvas)
            layoutHelper.drawDividers(canvas, width, height)
            layoutHelper.dispatchRoundBorderDraw(canvas)
        } catch (ignore: Throwable) {
            // unreasonable crash
        }
    }

    override fun updateTopDivider(topInsetLeft: Int, topInsetRight: Int, topDividerHeight: Int, topDividerColor: Int) {
        layoutHelper.updateTopDivider(topInsetLeft, topInsetRight, topDividerHeight, topDividerColor)
        invalidate()
    }

    override fun updateBottomDivider(bottomInsetLeft: Int, bottomInsetRight: Int, bottomDividerHeight: Int, bottomDividerColor: Int) {
        layoutHelper.updateBottomDivider(bottomInsetLeft, bottomInsetRight, bottomDividerHeight, bottomDividerColor)
        invalidate()
    }

    override fun updateLeftDivider(leftInsetTop: Int, leftInsetBottom: Int, leftDividerWidth: Int, leftDividerColor: Int) {
        layoutHelper.updateLeftDivider(leftInsetTop, leftInsetBottom, leftDividerWidth, leftDividerColor)
        invalidate()
    }

    override fun updateRightDivider(rightInsetTop: Int, rightInsetBottom: Int, rightDividerWidth: Int, rightDividerColor: Int) {
        layoutHelper.updateRightDivider(rightInsetTop, rightInsetBottom, rightDividerWidth, rightDividerColor)
        invalidate()
    }

    override fun onlyShowTopDivider(topInsetLeft: Int, topInsetRight: Int, topDividerHeight: Int, topDividerColor: Int) {
        layoutHelper.onlyShowTopDivider(topInsetLeft, topInsetRight, topDividerHeight, topDividerColor)
        invalidate()
    }

    override fun onlyShowBottomDivider(bottomInsetLeft: Int, bottomInsetRight: Int, bottomDividerHeight: Int, bottomDividerColor: Int) {
        layoutHelper.onlyShowBottomDivider(bottomInsetLeft, bottomInsetRight, bottomDividerHeight, bottomDividerColor)
        invalidate()
    }

    override fun onlyShowLeftDivider(leftInsetTop: Int, leftInsetBottom: Int, leftDividerWidth: Int, leftDividerColor: Int) {
        layoutHelper.onlyShowLeftDivider(leftInsetTop, leftInsetBottom, leftDividerWidth, leftDividerColor)
        invalidate()
    }

    override fun onlyShowRightDivider(rightInsetTop: Int, rightInsetBottom: Int, rightDividerWidth: Int, rightDividerColor: Int) {
        layoutHelper.onlyShowRightDivider(rightInsetTop, rightInsetBottom, rightDividerWidth, rightDividerColor)
        invalidate()
    }

    override fun setTopDividerAlpha(dividerAlpha: Int) {
        layoutHelper.setTopDividerAlpha(dividerAlpha)
        invalidate()
    }

    override fun setBottomDividerAlpha(dividerAlpha: Int) {
        layoutHelper.setBottomDividerAlpha(dividerAlpha)
        invalidate()
    }

    override fun setLeftDividerAlpha(dividerAlpha: Int) {
        layoutHelper.setLeftDividerAlpha(dividerAlpha)
        invalidate()
    }

    override fun setRightDividerAlpha(dividerAlpha: Int) {
        layoutHelper.setRightDividerAlpha(dividerAlpha)
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMeasureSpec1 = layoutHelper.getMeasuredWidthSpec(widthMeasureSpec)
        val heightMeasureSpec1 = layoutHelper.getMeasuredHeightSpec(heightMeasureSpec)
        super.onMeasure(widthMeasureSpec1, heightMeasureSpec1)
        val minW = layoutHelper.handleMiniWidth(widthMeasureSpec1, measuredWidth)
        val minH = layoutHelper.handleMiniHeight(heightMeasureSpec1, measuredHeight)
        if (widthMeasureSpec1 != minW || heightMeasureSpec1 != minH) {
            super.onMeasure(minW, minH)
        }
    }

    override fun setRadiusAndShadow(radius: Int, shadowElevation: Int, shadowAlpha: Float) {
        layoutHelper.setRadiusAndShadow(radius, shadowElevation, shadowAlpha)
    }

    override fun setRadiusAndShadow(radius: Int, @ClaShadowLayoutInf.HideRadiusSide hideRadiusSide: Int, shadowElevation: Int, shadowAlpha: Float) {
        layoutHelper.setRadiusAndShadow(radius, hideRadiusSide, shadowElevation, shadowAlpha)
    }

    override fun setRadiusAndShadow(radius: Int, hideRadiusSide: Int, shadowElevation: Int, shadowColor: Int, shadowAlpha: Float) {
        layoutHelper.setRadiusAndShadow(radius, hideRadiusSide, shadowElevation, shadowColor, shadowAlpha)
    }

    override fun setRadius(radius: Int, @ClaShadowLayoutInf.HideRadiusSide hideRadiusSide: Int) {
        layoutHelper.setRadius(radius, hideRadiusSide)
    }

    override var radius: Int
        get() = layoutHelper.radius
        set(radius) {
            layoutHelper.radius = radius
        }

    override fun setOutlineInset(left: Int, top: Int, right: Int, bottom: Int) {
        layoutHelper.setOutlineInset(left, top, right, bottom)
    }

    override fun setBorderColor(@ColorInt borderColor: Int) {
        layoutHelper.setBorderColor(borderColor)
        invalidate()
    }

    override fun setBorderWidth(borderWidth: Int) {
        layoutHelper.setBorderWidth(borderWidth)
        invalidate()
    }

    override fun setShowBorderOnlyBeforeL(showBorderOnlyBeforeL: Boolean) {
        layoutHelper.setShowBorderOnlyBeforeL(showBorderOnlyBeforeL)
        invalidate()
    }

    override var hideRadiusSide: Int
        get() = layoutHelper.hideRadiusSide
        set(hideRadiusSide) {
            layoutHelper.hideRadiusSide = hideRadiusSide
        }

    override fun setWidthLimit(widthLimit: Int): Boolean {
        if (layoutHelper.setWidthLimit(widthLimit)) {
            requestLayout()
            invalidate()
        }
        return true
    }

    override fun setHeightLimit(heightLimit: Int): Boolean {
        if (layoutHelper.setHeightLimit(heightLimit)) {
            requestLayout()
            invalidate()
        }
        return true
    }

    override fun setUseThemeGeneralShadowElevation() {
        layoutHelper.setUseThemeGeneralShadowElevation()
    }

    override fun setOutlineExcludePadding(outlineExcludePadding: Boolean) {
        layoutHelper.setOutlineExcludePadding(outlineExcludePadding)
    }

    override fun updateBottomSeparatorColor(color: Int) {
        layoutHelper.updateBottomSeparatorColor(color)
    }

    override fun updateLeftSeparatorColor(color: Int) {
        layoutHelper.updateLeftSeparatorColor(color)
    }

    override fun updateRightSeparatorColor(color: Int) {
        layoutHelper.updateRightSeparatorColor(color)
    }

    override fun updateTopSeparatorColor(color: Int) {
        layoutHelper.updateTopSeparatorColor(color)
    }

    override var shadowElevation: Int
        get() = layoutHelper.shadowElevation
        set(elevation) {
            layoutHelper.shadowElevation = elevation
        }
    override var shadowColor: Int
        get() = layoutHelper.shadowColor
        set(shadowColor) {
            layoutHelper.shadowColor = shadowColor
        }

    override fun setOuterNormalColor(color: Int) {
        layoutHelper.setOuterNormalColor(color)
    }

    override var shadowAlpha: Float
        get() = layoutHelper.shadowAlpha
        set(shadowAlpha) {
            layoutHelper.shadowAlpha = shadowAlpha
        }

    override fun hasBorder(): Boolean {
        return layoutHelper.hasBorder()
    }

    override fun hasLeftSeparator(): Boolean {
        return layoutHelper.hasLeftSeparator()
    }

    override fun hasTopSeparator(): Boolean {
        return layoutHelper.hasTopSeparator()
    }

    override fun hasRightSeparator(): Boolean {
        return layoutHelper.hasRightSeparator()
    }

    override fun hasBottomSeparator(): Boolean {
        return layoutHelper.hasBottomSeparator()
    }

    override fun getView(): View = this
}