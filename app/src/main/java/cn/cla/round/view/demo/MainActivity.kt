package cn.cla.round.view.demo

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import cn.cla.round.view.inf.ClaRoundStateType
import cn.cla.round.view.widget.ClaRoundTextView
import kotlin.concurrent.thread
import kotlin.math.abs
import kotlin.math.min


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvRound = findViewById<ClaRoundTextView>(R.id.tvRound)
        val tvPress = findViewById<ClaRoundTextView>(R.id.tvPress)
        val tvDisable = findViewById<ClaRoundTextView>(R.id.tvDisable)
        val tvBottomLine = findViewById<ClaRoundTextView>(R.id.tvBottomLine)
        val tvChangeLine = findViewById<ClaRoundTextView>(R.id.tvChangeLine)
        val btnSelect = findViewById<Button>(R.id.btnSelect)
        val btnFocus = findViewById<Button>(R.id.btnFocus)
        val btnActivated = findViewById<Button>(R.id.btnActivated)
        val btnChangeActivated = findViewById<Button>(R.id.btnChangeActivated)
        val btnDisable = findViewById<Button>(R.id.btnDisable)

        tvRound.setClaBackground(ClaRoundStateType.PRESSED) {
            val gd = GradientDrawable()
            gd.shape = GradientDrawable.RECTANGLE
            gd.cornerRadius = 12.dpf
            gd.setStroke(4, Color.RED) //设置宽度为10px的红色描边
            gd.gradientType =
                GradientDrawable.LINEAR_GRADIENT //设置线性渐变，除此之外还有：GradientDrawable.SWEEP_GRADIENT（扫描式渐变），GradientDrawable.RADIAL_GRADIENT（圆形渐变）
            gd.colors =
                intArrayOf(Color.RED, Color.BLUE, Color.BLACK, Color.WHITE, Color.YELLOW) //增加渐变效果需要使用setColors方法来设置颜色（中间可以增加多个颜色值）
            drawable = gd
            textColor = colorValue(R.color.white)
            textColorAlpha = 1f
        }

        tvRound.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                Toast.makeText(this, "tvRound获取焦点", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "tvRound失去焦点", Toast.LENGTH_SHORT).show()
            }

            if (hasFocus) {
                tvRound.postDelayed({
                    tvRound.clearFocus()
                }, 2000)
            }
        }

        btnSelect.setOnClickListener {
            tvRound.isSelected = !tvRound.isSelected
        }

        btnFocus.setOnClickListener {
            tvRound.requestFocus()
        }

        btnActivated.setOnClickListener {
            tvRound.isActivated = !tvRound.isActivated
        }

        btnChangeActivated.setOnClickListener {
            thread {
                runOnUiThread {
                    tvRound.setClaBackground(ClaRoundStateType.ACTIVATED) {
                        borderColor = colorValue(R.color.c1)
                        borderColorAlpha = 0.5f
                        borderWidth = 4.dp.toFloat()
                        bgColor = colorValue(R.color.c1)
                        bgColorAlpha = 0.2f
                        textColor = colorValue(R.color.c1)
                        textColorAlpha = 0.5f
                        radius = 15.dp.toFloat()
                    }
                }

                Thread.sleep(2000)

                runOnUiThread {
                    tvRound.setClaBackground(ClaRoundStateType.ACTIVATED) {
                        drawable = drawableValue(R.mipmap.ic_launcher)
                    }
                }

                Thread.sleep(2000)

                runOnUiThread {
                    tvRound.setClaBackground(ClaRoundStateType.ACTIVATED) {
                        val gd = GradientDrawable()
                        gd.shape = GradientDrawable.RECTANGLE
                        gd.cornerRadius = 12.dp.toFloat()
                        gd.setStroke(4, Color.RED) //设置宽度为10px的红色描边
                        gd.gradientType =
                            GradientDrawable.LINEAR_GRADIENT //设置线性渐变，除此之外还有：GradientDrawable.SWEEP_GRADIENT（扫描式渐变），GradientDrawable.RADIAL_GRADIENT（圆形渐变）
                        gd.colors =
                            intArrayOf(Color.RED, Color.BLUE, Color.BLACK, Color.WHITE, Color.YELLOW) //增加渐变效果需要使用setColors方法来设置颜色（中间可以增加多个颜色值）
                        drawable = gd
                        textColor = colorValue(R.color.white)
                        textColorAlpha = 1f
                    }
                }
            }
        }

        btnDisable.setOnClickListener {
            tvRound.isEnabled = !tvRound.isEnabled
        }

        tvDisable.setOnClickListener {
            tvDisable.isEnabled = false
        }

        tvChangeLine.setOnClickListener {
            tvBottomLine.resetClaLine {
                lineDashWidth = 0f
                lineLeftSpace = 2.dp.toFloat()
                lineColor = colorValue(R.color.c3)
            }
        }
    }
}


val Int.dp: Int
    get() = dp2px(this.toFloat())

val Int.dpf: Float
    get() = dp2px(this.toFloat()).toFloat()

fun dp2px(dpValue: Float): Int {
    val scale = Resources.getSystem().displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

/**
 * 转换颜色值
 *
 * @param percent 透明度
 */
fun Context.colorValue(@ColorRes colorRes: Int, percent: Float = 1f) = ContextCompat.getColor(this, colorRes).run {
    kotlin.runCatching {
        val r = Color.red(this)
        val g = Color.green(this)
        val b = Color.blue(this)
        val a = Color.alpha(this)
        val alpha = min(abs((a * percent).toInt()), 255)
        Color.argb(alpha, r, g, b)
    }.getOrElse {
        this
    }
}

fun Context.changeSvgColor(drawable: Drawable?, @ColorRes colorRes: Int) = drawable?.apply {
    val ctx = this@changeSvgColor
    try {
        DrawableCompat.setTint(mutate(), ContextCompat.getColor(ctx, colorRes))
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.drawableValue(
    @DrawableRes res: Int?,
    @ColorRes colorRes: Int? = null
) = res?.run {

    val ctx = this@drawableValue

    try {
        val drawable = ContextCompat.getDrawable(ctx, res)
        colorRes?.run { ctx.changeSvgColor(drawable, this) } ?: drawable
    } catch (e: Exception) {
        null
    }
}