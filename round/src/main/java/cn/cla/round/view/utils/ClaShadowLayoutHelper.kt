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

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Resources.Theme
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import cn.cla.round.view.R
import cn.cla.round.view.inf.ClaShadowLayoutInf
import java.lang.ref.WeakReference

/**
 * @author 阴影 复制的qmui的代码
 * @date 2017-03-10
 */
class ClaShadowLayoutHelper(
    private val mContext: Context,
    attrs: AttributeSet?,
    defAttr: Int,
    defStyleRes: Int,
    owner: View?
) : ClaShadowLayoutInf {

    companion object {
        const val RADIUS_OF_HALF_VIEW_HEIGHT = -1
        const val RADIUS_OF_HALF_VIEW_WIDTH = -2
    }

    // size
    private var mWidthLimit = 0
    private var mHeightLimit = 0
    private var mWidthMini = 0
    private var mHeightMini = 0

    // divider
    private var mTopDividerHeight = 0
    private var mTopDividerInsetLeft = 0
    private var mTopDividerInsetRight = 0
    private var mTopDividerColor: Int
    private var mTopDividerAlpha = 255
    private var mBottomDividerHeight = 0
    private var mBottomDividerInsetLeft = 0
    private var mBottomDividerInsetRight = 0
    private var mBottomDividerColor: Int
    private var mBottomDividerAlpha = 255
    private var mLeftDividerWidth = 0
    private var mLeftDividerInsetTop = 0
    private var mLeftDividerInsetBottom = 0
    private var mLeftDividerColor = 0
    private var mLeftDividerAlpha = 255
    private var mRightDividerWidth = 0
    private var mRightDividerInsetTop = 0
    private var mRightDividerInsetBottom = 0
    private var mRightDividerColor = 0
    private var mRightDividerAlpha = 255
    private var mDividerPaint: Paint? = null

    // round
    private val mClipPaint: Paint
    private val mMode: PorterDuffXfermode
    private var mRadius = 0

    @ClaShadowLayoutInf.HideRadiusSide
    private var mHideRadiusSide = ClaShadowLayoutInf.HIDE_RADIUS_SIDE_NONE
    private var mRadiusArray: FloatArray? = null
    private var mShouldUseRadiusArray = false
    private val mBorderRect: RectF
    private var mBorderColor = 0
    private var mBorderWidth = 1
    private var mOuterNormalColor = 0
    private val mOwner: WeakReference<View?> = WeakReference(owner)
    private var mIsOutlineExcludePadding = false
    private val mPath = Path()

    // shadow
    private var mIsShowBorderOnlyBeforeL = true
    private var mShadowElevation = 0
    private var mShadowAlpha: Float
    private var mShadowColor = Color.BLACK

    // outline inset
    private var mOutlineInsetLeft = 0
    private var mOutlineInsetRight = 0
    private var mOutlineInsetTop = 0
    private var mOutlineInsetBottom = 0

    constructor(context: Context, attrs: AttributeSet?, defAttr: Int, owner: View?) : this(context, attrs, defAttr, 0, owner) {}

    init {
        mTopDividerColor =
            ContextCompat.getColor(mContext, R.color.cla_shadow_config_color_separator)
        mBottomDividerColor = mTopDividerColor
        mMode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        mClipPaint = Paint()
        mClipPaint.isAntiAlias = true
        mShadowAlpha =
            ShadowUIResHelper.getAttrFloatValue(mContext, R.attr.cla_general_shadow_alpha)
        mBorderRect = RectF()
        var radius = 0
        var shadow = 0
        var useThemeGeneralShadowElevation = false
        if (null != attrs || defAttr != 0 || defStyleRes != 0) {
            val ta =
                mContext.obtainStyledAttributes(attrs, R.styleable.ClaShadowConstraintLayout, defAttr, defStyleRes)
            val count = ta.indexCount
            for (i in 0 until count) {
                val index = ta.getIndex(i)
                if (index == R.styleable.ClaShadowConstraintLayout_android_maxWidth) {
                    mWidthLimit = ta.getDimensionPixelSize(index, mWidthLimit)
                } else if (index == R.styleable.ClaShadowConstraintLayout_android_maxHeight) {
                    mHeightLimit = ta.getDimensionPixelSize(index, mHeightLimit)
                } else if (index == R.styleable.ClaShadowConstraintLayout_android_minWidth) {
                    mWidthMini = ta.getDimensionPixelSize(index, mWidthMini)
                } else if (index == R.styleable.ClaShadowConstraintLayout_android_minHeight) {
                    mHeightMini = ta.getDimensionPixelSize(index, mHeightMini)
                } else if (index == R.styleable.ClaShadowConstraintLayout_cla_topDividerColor) {
                    mTopDividerColor = ta.getColor(index, mTopDividerColor)
                } else if (index == R.styleable.ClaShadowConstraintLayout_cla_topDividerHeight) {
                    mTopDividerHeight = ta.getDimensionPixelSize(index, mTopDividerHeight)
                } else if (index == R.styleable.ClaShadowConstraintLayout_cla_topDividerInsetLeft) {
                    mTopDividerInsetLeft = ta.getDimensionPixelSize(index, mTopDividerInsetLeft)
                } else if (index == R.styleable.ClaShadowConstraintLayout_cla_topDividerInsetRight) {
                    mTopDividerInsetRight = ta.getDimensionPixelSize(index, mTopDividerInsetRight)
                } else if (index == R.styleable.ClaShadowConstraintLayout_cla_bottomDividerColor) {
                    mBottomDividerColor = ta.getColor(index, mBottomDividerColor)
                } else if (index == R.styleable.ClaShadowConstraintLayout_cla_bottomDividerHeight) {
                    mBottomDividerHeight = ta.getDimensionPixelSize(index, mBottomDividerHeight)
                } else if (index == R.styleable.ClaShadowConstraintLayout_cla_bottomDividerInsetLeft) {
                    mBottomDividerInsetLeft =
                        ta.getDimensionPixelSize(index, mBottomDividerInsetLeft)
                } else if (index == R.styleable.ClaShadowConstraintLayout_cla_bottomDividerInsetRight) {
                    mBottomDividerInsetRight =
                        ta.getDimensionPixelSize(index, mBottomDividerInsetRight)
                } else if (index == R.styleable.ClaShadowConstraintLayout_cla_leftDividerColor) {
                    mLeftDividerColor = ta.getColor(index, mLeftDividerColor)
                } else if (index == R.styleable.ClaShadowConstraintLayout_cla_leftDividerWidth) {
                    mLeftDividerWidth = ta.getDimensionPixelSize(index, mLeftDividerWidth)
                } else if (index == R.styleable.ClaShadowConstraintLayout_cla_leftDividerInsetTop) {
                    mLeftDividerInsetTop = ta.getDimensionPixelSize(index, mLeftDividerInsetTop)
                } else if (index == R.styleable.ClaShadowConstraintLayout_cla_leftDividerInsetBottom) {
                    mLeftDividerInsetBottom =
                        ta.getDimensionPixelSize(index, mLeftDividerInsetBottom)
                } else if (index == R.styleable.ClaShadowConstraintLayout_cla_rightDividerColor) {
                    mRightDividerColor = ta.getColor(index, mRightDividerColor)
                } else if (index == R.styleable.ClaShadowConstraintLayout_cla_rightDividerWidth) {
                    mRightDividerWidth = ta.getDimensionPixelSize(index, mRightDividerWidth)
                } else if (index == R.styleable.ClaShadowConstraintLayout_cla_rightDividerInsetTop) {
                    mRightDividerInsetTop = ta.getDimensionPixelSize(index, mRightDividerInsetTop)
                } else if (index == R.styleable.ClaShadowConstraintLayout_cla_rightDividerInsetBottom) {
                    mRightDividerInsetBottom =
                        ta.getDimensionPixelSize(index, mRightDividerInsetBottom)
                } else if (index == R.styleable.ClaShadowConstraintLayout_cla_borderColor) {
                    mBorderColor = ta.getColor(index, mBorderColor)
                } else if (index == R.styleable.ClaShadowConstraintLayout_cla_borderWidth) {
                    mBorderWidth = ta.getDimensionPixelSize(index, mBorderWidth)
                } else if (index == R.styleable.ClaShadowConstraintLayout_cla_radius) {
                    radius = ta.getDimensionPixelSize(index, 0)
                } else if (index == R.styleable.ClaShadowConstraintLayout_cla_outerNormalColor) {
                    mOuterNormalColor = ta.getColor(index, mOuterNormalColor)
                } else if (index == R.styleable.ClaShadowConstraintLayout_cla_hideRadiusSide) {
                    mHideRadiusSide = ta.getInt(index, mHideRadiusSide)
                } else if (index == R.styleable.ClaShadowConstraintLayout_cla_showBorderOnlyBeforeL) {
                    mIsShowBorderOnlyBeforeL = ta.getBoolean(index, mIsShowBorderOnlyBeforeL)
                } else if (index == R.styleable.ClaShadowConstraintLayout_cla_shadowElevation) {
                    shadow = ta.getDimensionPixelSize(index, shadow)
                } else if (index == R.styleable.ClaShadowConstraintLayout_cla_shadowAlpha) {
                    mShadowAlpha = ta.getFloat(index, mShadowAlpha)
                } else if (index == R.styleable.ClaShadowConstraintLayout_cla_useThemeGeneralShadowElevation) {
                    useThemeGeneralShadowElevation = ta.getBoolean(index, false)
                } else if (index == R.styleable.ClaShadowConstraintLayout_cla_outlineInsetLeft) {
                    mOutlineInsetLeft = ta.getDimensionPixelSize(index, 0)
                } else if (index == R.styleable.ClaShadowConstraintLayout_cla_outlineInsetRight) {
                    mOutlineInsetRight = ta.getDimensionPixelSize(index, 0)
                } else if (index == R.styleable.ClaShadowConstraintLayout_cla_outlineInsetTop) {
                    mOutlineInsetTop = ta.getDimensionPixelSize(index, 0)
                } else if (index == R.styleable.ClaShadowConstraintLayout_cla_outlineInsetBottom) {
                    mOutlineInsetBottom = ta.getDimensionPixelSize(index, 0)
                } else if (index == R.styleable.ClaShadowConstraintLayout_cla_outlineExcludePadding) {
                    mIsOutlineExcludePadding = ta.getBoolean(index, false)
                }
            }
            ta.recycle()
        }
        if (shadow == 0 && useThemeGeneralShadowElevation) {
            shadow = ShadowUIResHelper.getAttrDimen(mContext, R.attr.cla_general_shadow_elevation)
        }
        setRadiusAndShadow(radius, mHideRadiusSide, shadow, mShadowAlpha)
    }

    override fun setUseThemeGeneralShadowElevation() {
        mShadowElevation =
            ShadowUIResHelper.getAttrDimen(mContext, R.attr.cla_general_shadow_elevation)
        setRadiusAndShadow(mRadius, mHideRadiusSide, mShadowElevation, mShadowAlpha)
    }

    override fun setOutlineExcludePadding(outlineExcludePadding: Boolean) {
        if (useFeature()) {
            val owner = mOwner.get() ?: return
            mIsOutlineExcludePadding = outlineExcludePadding
            owner.invalidateOutline()
        }
    }

    override fun setWidthLimit(widthLimit: Int): Boolean {
        if (mWidthLimit != widthLimit) {
            mWidthLimit = widthLimit
            return true
        }
        return false
    }

    override fun setHeightLimit(heightLimit: Int): Boolean {
        if (mHeightLimit != heightLimit) {
            mHeightLimit = heightLimit
            return true
        }
        return false
    }

    override fun updateLeftSeparatorColor(color: Int) {
        if (mLeftDividerColor != color) {
            mLeftDividerColor = color
            invalidate()
        }
    }

    override fun updateBottomSeparatorColor(color: Int) {
        if (mBottomDividerColor != color) {
            mBottomDividerColor = color
            invalidate()
        }
    }

    override fun updateTopSeparatorColor(color: Int) {
        if (mTopDividerColor != color) {
            mTopDividerColor = color
            invalidate()
        }
    }

    override fun updateRightSeparatorColor(color: Int) {
        if (mRightDividerColor != color) {
            mRightDividerColor = color
            invalidate()
        }
    }

    override var shadowElevation: Int
        get() = mShadowElevation
        set(elevation) {
            if (mShadowElevation == elevation) {
                return
            }
            mShadowElevation = elevation
            invalidateOutline()
        }
    override var shadowAlpha: Float
        get() = mShadowAlpha
        set(shadowAlpha) {
            if (mShadowAlpha == shadowAlpha) {
                return
            }
            mShadowAlpha = shadowAlpha
            invalidateOutline()
        }
    override var shadowColor: Int
        get() = mShadowColor
        set(shadowColor) {
            if (mShadowColor == shadowColor) {
                return
            }
            mShadowColor = shadowColor
            setShadowColorInner(mShadowColor)
        }

    override fun setOutlineInset(left: Int, top: Int, right: Int, bottom: Int) {
        if (useFeature()) {
            val owner = mOwner.get() ?: return
            mOutlineInsetLeft = left
            mOutlineInsetRight = right
            mOutlineInsetTop = top
            mOutlineInsetBottom = bottom
            owner.invalidateOutline()
        }
    }

    override fun setShowBorderOnlyBeforeL(showBorderOnlyBeforeL: Boolean) {
        mIsShowBorderOnlyBeforeL = showBorderOnlyBeforeL
        invalidate()
    }

    private fun setShadowColorInner(shadowColor: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val owner = mOwner.get() ?: return
            owner.outlineAmbientShadowColor = shadowColor
            owner.outlineSpotShadowColor = shadowColor
        }
    }

    private fun invalidateOutline() {
        if (useFeature()) {
            val owner = mOwner.get() ?: return
            if (mShadowElevation == 0) {
                owner.elevation = 0f
            } else {
                owner.elevation = mShadowElevation.toFloat()
            }
            owner.invalidateOutline()
        }
    }

    private fun invalidate() {
        val owner = mOwner.get() ?: return
        owner.invalidate()
    }

    override var hideRadiusSide: Int
        get() = mHideRadiusSide
        set(hideRadiusSide) {
            if (mHideRadiusSide == hideRadiusSide) {
                return
            }
            setRadiusAndShadow(mRadius, hideRadiusSide, mShadowElevation, mShadowAlpha)
        }

    override fun setRadius(radius: Int, @ClaShadowLayoutInf.HideRadiusSide hideRadiusSide: Int) {
        if (mRadius == radius && hideRadiusSide == mHideRadiusSide) {
            return
        }
        setRadiusAndShadow(radius, hideRadiusSide, mShadowElevation, mShadowAlpha)
    }

    override var radius: Int
        get() = mRadius
        set(radius) {
            if (mRadius != radius) {
                setRadiusAndShadow(radius, mShadowElevation, mShadowAlpha)
            }
        }

    override fun setRadiusAndShadow(radius: Int, shadowElevation: Int, shadowAlpha: Float) {
        setRadiusAndShadow(radius, mHideRadiusSide, shadowElevation, shadowAlpha)
    }

    override fun setRadiusAndShadow(radius: Int, @ClaShadowLayoutInf.HideRadiusSide hideRadiusSide: Int, shadowElevation: Int, shadowAlpha: Float) {
        setRadiusAndShadow(radius, hideRadiusSide, shadowElevation, mShadowColor, shadowAlpha)
    }

    override fun setRadiusAndShadow(radius: Int, hideRadiusSide: Int, shadowElevation: Int, shadowColor: Int, shadowAlpha: Float) {
        val owner = mOwner.get() ?: return
        mRadius = radius
        mHideRadiusSide = hideRadiusSide
        mShouldUseRadiusArray = isRadiusWithSideHidden
        mShadowElevation = shadowElevation
        mShadowAlpha = shadowAlpha
        mShadowColor = shadowColor
        if (useFeature()) {
            if (mShadowElevation == 0 || mShouldUseRadiusArray) {
                owner.elevation = 0f
            } else {
                owner.elevation = mShadowElevation.toFloat()
            }
            setShadowColorInner(mShadowColor)
            owner.outlineProvider = object : ViewOutlineProvider() {
                @TargetApi(21) override fun getOutline(view: View, outline: Outline) {
                    val w = view.width
                    val h = view.height
                    if (w == 0 || h == 0) {
                        return
                    }
                    var radius = realRadius.toFloat()
                    val min = Math.min(w, h)
                    if (radius * 2 > min) {
                        // 解决 OnePlus 3T 8.0 上显示变形
                        radius = min / 2f
                    }
                    if (mShouldUseRadiusArray) {
                        var left = 0
                        var top = 0
                        var right = w
                        var bottom = h
                        if (mHideRadiusSide == ClaShadowLayoutInf.HIDE_RADIUS_SIDE_LEFT) {
                            left -= radius.toInt()
                        } else if (mHideRadiusSide == ClaShadowLayoutInf.HIDE_RADIUS_SIDE_TOP) {
                            top -= radius.toInt()
                        } else if (mHideRadiusSide == ClaShadowLayoutInf.HIDE_RADIUS_SIDE_RIGHT) {
                            right += radius.toInt()
                        } else if (mHideRadiusSide == ClaShadowLayoutInf.HIDE_RADIUS_SIDE_BOTTOM) {
                            bottom += radius.toInt()
                        }
                        outline.setRoundRect(
                            left, top,
                            right, bottom, radius
                        )
                        return
                    }
                    var top = mOutlineInsetTop
                    var bottom = Math.max(top + 1, h - mOutlineInsetBottom)
                    var left = mOutlineInsetLeft
                    var right = w - mOutlineInsetRight
                    if (mIsOutlineExcludePadding) {
                        left += view.paddingLeft
                        top += view.paddingTop
                        right = Math.max(left + 1, right - view.paddingRight)
                        bottom = Math.max(top + 1, bottom - view.paddingBottom)
                    }
                    var shadowAlpha = mShadowAlpha
                    if (mShadowElevation == 0) {
                        // outline.setAlpha will work even if shadowElevation == 0
                        shadowAlpha = 1f
                    }
                    outline.alpha = shadowAlpha
                    if (radius <= 0) {
                        outline.setRect(
                            left, top,
                            right, bottom
                        )
                    } else {
                        outline.setRoundRect(
                            left, top,
                            right, bottom, radius
                        )
                    }
                }
            }
            owner.clipToOutline =
                mRadius == RADIUS_OF_HALF_VIEW_WIDTH || mRadius == RADIUS_OF_HALF_VIEW_HEIGHT || mRadius > 0
        }
        owner.invalidate()
    }

    /**
     * 有radius, 但是有一边不显示radius。
     *
     * @return
     */
    val isRadiusWithSideHidden: Boolean
        get() = (mRadius == RADIUS_OF_HALF_VIEW_HEIGHT || mRadius == RADIUS_OF_HALF_VIEW_WIDTH || mRadius > 0) && mHideRadiusSide != ClaShadowLayoutInf.HIDE_RADIUS_SIDE_NONE

    override fun updateTopDivider(topInsetLeft: Int, topInsetRight: Int, topDividerHeight: Int, topDividerColor: Int) {
        mTopDividerInsetLeft = topInsetLeft
        mTopDividerInsetRight = topInsetRight
        mTopDividerHeight = topDividerHeight
        mTopDividerColor = topDividerColor
    }

    override fun updateBottomDivider(bottomInsetLeft: Int, bottomInsetRight: Int, bottomDividerHeight: Int, bottomDividerColor: Int) {
        mBottomDividerInsetLeft = bottomInsetLeft
        mBottomDividerInsetRight = bottomInsetRight
        mBottomDividerColor = bottomDividerColor
        mBottomDividerHeight = bottomDividerHeight
    }

    override fun updateLeftDivider(leftInsetTop: Int, leftInsetBottom: Int, leftDividerWidth: Int, leftDividerColor: Int) {
        mLeftDividerInsetTop = leftInsetTop
        mLeftDividerInsetBottom = leftInsetBottom
        mLeftDividerWidth = leftDividerWidth
        mLeftDividerColor = leftDividerColor
    }

    override fun updateRightDivider(rightInsetTop: Int, rightInsetBottom: Int, rightDividerWidth: Int, rightDividerColor: Int) {
        mRightDividerInsetTop = rightInsetTop
        mRightDividerInsetBottom = rightInsetBottom
        mRightDividerWidth = rightDividerWidth
        mRightDividerColor = rightDividerColor
    }

    override fun onlyShowTopDivider(
        topInsetLeft: Int, topInsetRight: Int,
        topDividerHeight: Int, topDividerColor: Int
    ) {
        updateTopDivider(topInsetLeft, topInsetRight, topDividerHeight, topDividerColor)
        mLeftDividerWidth = 0
        mRightDividerWidth = 0
        mBottomDividerHeight = 0
    }

    override fun onlyShowBottomDivider(
        bottomInsetLeft: Int, bottomInsetRight: Int,
        bottomDividerHeight: Int, bottomDividerColor: Int
    ) {
        updateBottomDivider(bottomInsetLeft, bottomInsetRight, bottomDividerHeight, bottomDividerColor)
        mLeftDividerWidth = 0
        mRightDividerWidth = 0
        mTopDividerHeight = 0
    }

    override fun onlyShowLeftDivider(leftInsetTop: Int, leftInsetBottom: Int, leftDividerWidth: Int, leftDividerColor: Int) {
        updateLeftDivider(leftInsetTop, leftInsetBottom, leftDividerWidth, leftDividerColor)
        mRightDividerWidth = 0
        mTopDividerHeight = 0
        mBottomDividerHeight = 0
    }

    override fun onlyShowRightDivider(rightInsetTop: Int, rightInsetBottom: Int, rightDividerWidth: Int, rightDividerColor: Int) {
        updateRightDivider(rightInsetTop, rightInsetBottom, rightDividerWidth, rightDividerColor)
        mLeftDividerWidth = 0
        mTopDividerHeight = 0
        mBottomDividerHeight = 0
    }

    override fun setTopDividerAlpha(dividerAlpha: Int) {
        mTopDividerAlpha = dividerAlpha
    }

    override fun setBottomDividerAlpha(dividerAlpha: Int) {
        mBottomDividerAlpha = dividerAlpha
    }

    override fun setLeftDividerAlpha(dividerAlpha: Int) {
        mLeftDividerAlpha = dividerAlpha
    }

    override fun setRightDividerAlpha(dividerAlpha: Int) {
        mRightDividerAlpha = dividerAlpha
    }

    fun handleMiniWidth(widthMeasureSpec: Int, measuredWidth: Int): Int {
        return if (View.MeasureSpec.getMode(widthMeasureSpec) != View.MeasureSpec.EXACTLY
            && measuredWidth < mWidthMini
        ) {
            View.MeasureSpec.makeMeasureSpec(mWidthMini, View.MeasureSpec.EXACTLY)
        } else widthMeasureSpec
    }

    fun handleMiniHeight(heightMeasureSpec: Int, measuredHeight: Int): Int {
        return if (View.MeasureSpec.getMode(heightMeasureSpec) != View.MeasureSpec.EXACTLY
            && measuredHeight < mHeightMini
        ) {
            View.MeasureSpec.makeMeasureSpec(mHeightMini, View.MeasureSpec.EXACTLY)
        } else heightMeasureSpec
    }

    fun getMeasuredWidthSpec(widthMeasureSpec: Int): Int {
        var widthMeasureSpec = widthMeasureSpec
        if (mWidthLimit > 0) {
            val size = View.MeasureSpec.getSize(widthMeasureSpec)
            if (size > mWidthLimit) {
                val mode = View.MeasureSpec.getMode(widthMeasureSpec)
                widthMeasureSpec = if (mode == View.MeasureSpec.AT_MOST) {
                    View.MeasureSpec.makeMeasureSpec(mWidthLimit, View.MeasureSpec.AT_MOST)
                } else {
                    View.MeasureSpec.makeMeasureSpec(mWidthLimit, View.MeasureSpec.EXACTLY)
                }
            }
        }
        return widthMeasureSpec
    }

    fun getMeasuredHeightSpec(heightMeasureSpec: Int): Int {
        var heightMeasureSpec = heightMeasureSpec
        if (mHeightLimit > 0) {
            val size = View.MeasureSpec.getSize(heightMeasureSpec)
            if (size > mHeightLimit) {
                val mode = View.MeasureSpec.getMode(heightMeasureSpec)
                heightMeasureSpec = if (mode == View.MeasureSpec.AT_MOST) {
                    View.MeasureSpec.makeMeasureSpec(mWidthLimit, View.MeasureSpec.AT_MOST)
                } else {
                    View.MeasureSpec.makeMeasureSpec(mWidthLimit, View.MeasureSpec.EXACTLY)
                }
            }
        }
        return heightMeasureSpec
    }

    override fun setBorderColor(@ColorInt borderColor: Int) {
        mBorderColor = borderColor
    }

    override fun setBorderWidth(borderWidth: Int) {
        mBorderWidth = borderWidth
    }

    override fun setOuterNormalColor(color: Int) {
        mOuterNormalColor = color
        val owner = mOwner.get()
        owner?.invalidate()
    }

    override fun hasTopSeparator(): Boolean {
        return mTopDividerHeight > 0
    }

    override fun hasRightSeparator(): Boolean {
        return mRightDividerWidth > 0
    }

    override fun hasBottomSeparator(): Boolean {
        return mBottomDividerHeight > 0
    }

    override fun hasLeftSeparator(): Boolean {
        return mLeftDividerWidth > 0
    }

    override fun hasBorder(): Boolean {
        return mBorderWidth > 0
    }

    fun drawDividers(canvas: Canvas, w: Int, h: Int) {
        val owner = mOwner.get() ?: return
        if (mDividerPaint == null &&
            (mTopDividerHeight > 0 || mBottomDividerHeight > 0 || mLeftDividerWidth > 0 || mRightDividerWidth > 0)
        ) {
            mDividerPaint = Paint()
        }
        canvas.save()
        canvas.translate(owner.scrollX.toFloat(), owner.scrollY.toFloat())
        if (mTopDividerHeight > 0) {
            mDividerPaint!!.strokeWidth = mTopDividerHeight.toFloat()
            mDividerPaint!!.color = mTopDividerColor
            if (mTopDividerAlpha < 255) {
                mDividerPaint!!.alpha = mTopDividerAlpha
            }
            val y = mTopDividerHeight / 2f
            canvas.drawLine(mTopDividerInsetLeft.toFloat(), y, (w - mTopDividerInsetRight).toFloat(), y, mDividerPaint!!)
        }
        if (mBottomDividerHeight > 0) {
            mDividerPaint!!.strokeWidth = mBottomDividerHeight.toFloat()
            mDividerPaint!!.color = mBottomDividerColor
            if (mBottomDividerAlpha < 255) {
                mDividerPaint!!.alpha = mBottomDividerAlpha
            }
            val y = Math.floor((h - mBottomDividerHeight / 2f).toDouble()).toFloat()
            canvas.drawLine(mBottomDividerInsetLeft.toFloat(), y, (w - mBottomDividerInsetRight).toFloat(), y, mDividerPaint!!)
        }
        if (mLeftDividerWidth > 0) {
            mDividerPaint!!.strokeWidth = mLeftDividerWidth.toFloat()
            mDividerPaint!!.color = mLeftDividerColor
            if (mLeftDividerAlpha < 255) {
                mDividerPaint!!.alpha = mLeftDividerAlpha
            }
            val x = mLeftDividerWidth / 2f
            canvas.drawLine(x, mLeftDividerInsetTop.toFloat(), x, (h - mLeftDividerInsetBottom).toFloat(), mDividerPaint!!)
        }
        if (mRightDividerWidth > 0) {
            mDividerPaint!!.strokeWidth = mRightDividerWidth.toFloat()
            mDividerPaint!!.color = mRightDividerColor
            if (mRightDividerAlpha < 255) {
                mDividerPaint!!.alpha = mRightDividerAlpha
            }
            val x = Math.floor((w - mRightDividerWidth / 2f).toDouble()).toFloat()
            canvas.drawLine(x, mRightDividerInsetTop.toFloat(), x, (h - mRightDividerInsetBottom).toFloat(), mDividerPaint!!)
        }
        canvas.restore()
    }

    private val realRadius: Int
        private get() {
            val owner = mOwner.get() ?: return mRadius
            val radius: Int
            radius = if (mRadius == RADIUS_OF_HALF_VIEW_HEIGHT) {
                owner.height / 2
            } else if (mRadius == RADIUS_OF_HALF_VIEW_WIDTH) {
                owner.width / 2
            } else {
                mRadius
            }
            return radius
        }

    fun dispatchRoundBorderDraw(canvas: Canvas) {
        val owner = mOwner.get() ?: return
        val radius = realRadius
        val needCheckFakeOuterNormalDraw = radius > 0 && !useFeature() && mOuterNormalColor != 0
        val needDrawBorder = mBorderWidth > 0 && mBorderColor != 0
        if (!needCheckFakeOuterNormalDraw && !needDrawBorder) {
            return
        }
        if (mIsShowBorderOnlyBeforeL && useFeature() && mShadowElevation != 0) {
            return
        }
        val width = canvas.width
        val height = canvas.height
        canvas.save()
        canvas.translate(owner.scrollX.toFloat(), owner.scrollY.toFloat())

        // react
        val halfBorderWith = mBorderWidth / 2f
        if (mIsOutlineExcludePadding) {
            mBorderRect[owner.paddingLeft + halfBorderWith, owner.paddingTop + halfBorderWith, width - owner.paddingRight - halfBorderWith] =
                height - owner.paddingBottom - halfBorderWith
        } else {
            mBorderRect[halfBorderWith, halfBorderWith, width - halfBorderWith] =
                height - halfBorderWith
        }
        if (mShouldUseRadiusArray) {
            if (mRadiusArray == null) {
                mRadiusArray = FloatArray(8)
            }
            if (mHideRadiusSide == ClaShadowLayoutInf.HIDE_RADIUS_SIDE_TOP) {
                mRadiusArray!![4] = radius.toFloat()
                mRadiusArray!![5] = radius.toFloat()
                mRadiusArray!![6] = radius.toFloat()
                mRadiusArray!![7] = radius.toFloat()
            } else if (mHideRadiusSide == ClaShadowLayoutInf.HIDE_RADIUS_SIDE_RIGHT) {
                mRadiusArray!![0] = radius.toFloat()
                mRadiusArray!![1] = radius.toFloat()
                mRadiusArray!![6] = radius.toFloat()
                mRadiusArray!![7] = radius.toFloat()
            } else if (mHideRadiusSide == ClaShadowLayoutInf.HIDE_RADIUS_SIDE_BOTTOM) {
                mRadiusArray!![0] = radius.toFloat()
                mRadiusArray!![1] = radius.toFloat()
                mRadiusArray!![2] = radius.toFloat()
                mRadiusArray!![3] = radius.toFloat()
            } else if (mHideRadiusSide == ClaShadowLayoutInf.HIDE_RADIUS_SIDE_LEFT) {
                mRadiusArray!![2] = radius.toFloat()
                mRadiusArray!![3] = radius.toFloat()
                mRadiusArray!![4] = radius.toFloat()
                mRadiusArray!![5] = radius.toFloat()
            }
        }
        if (needCheckFakeOuterNormalDraw) {
            val layerId =
                canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null, Canvas.ALL_SAVE_FLAG)
            canvas.drawColor(mOuterNormalColor)
            mClipPaint.color = mOuterNormalColor
            mClipPaint.style = Paint.Style.FILL
            mClipPaint.xfermode = mMode
            if (!mShouldUseRadiusArray) {
                canvas.drawRoundRect(mBorderRect, radius.toFloat(), radius.toFloat(), mClipPaint)
            } else {
                drawRoundRect(canvas, mBorderRect, mRadiusArray, mClipPaint)
            }
            mClipPaint.xfermode = null
            canvas.restoreToCount(layerId)
        }
        if (needDrawBorder) {
            mClipPaint.color = mBorderColor
            mClipPaint.strokeWidth = mBorderWidth.toFloat()
            mClipPaint.style = Paint.Style.STROKE
            if (mShouldUseRadiusArray) {
                drawRoundRect(canvas, mBorderRect, mRadiusArray, mClipPaint)
            } else if (radius <= 0) {
                canvas.drawRect(mBorderRect, mClipPaint)
            } else {
                canvas.drawRoundRect(mBorderRect, radius.toFloat(), radius.toFloat(), mClipPaint)
            }
        }
        canvas.restore()
    }

    private fun drawRoundRect(canvas: Canvas, rect: RectF, radiusArray: FloatArray?, paint: Paint) {
        mPath.reset()
        mPath.addRoundRect(rect, radiusArray!!, Path.Direction.CW)
        canvas.drawPath(mPath, paint)
    }

    private fun useFeature(): Boolean {
        return Build.VERSION.SDK_INT >= 21
    }
}

private object ShadowUIResHelper {

    private var sTmpValue: TypedValue? = null

    fun getAttrFloatValue(context: Context, attr: Int): Float {
        return getAttrFloatValue(context.theme, attr)
    }

    fun getAttrFloatValue(theme: Theme, attr: Int): Float {
        if (sTmpValue == null) {
            sTmpValue = TypedValue()
        }
        return if (!theme.resolveAttribute(attr, sTmpValue, true)) {
            0f
        } else sTmpValue!!.float
    }

    fun getAttrDimen(context: Context, attrRes: Int): Int {
        if (sTmpValue == null) {
            sTmpValue = TypedValue()
        }
        return if (!context.theme.resolveAttribute(attrRes, sTmpValue, true)) {
            0
        } else TypedValue.complexToDimensionPixelSize(sTmpValue!!.data, getDisplayMetrics(context))
    }


    /** 获取 DisplayMetrics */
    fun getDisplayMetrics(context: Context): DisplayMetrics? {
        return context.resources.displayMetrics
    }
}