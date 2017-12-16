package com.kimboo.androidjobsnewsletter.ui.components.badgeimageview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import com.kimboo.androidjobsnewsletter.R

/**
 * Created by Agustin Tomas Larghi on 16/12/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
class BadgeImageView : AppCompatImageView {

    private var displayZeroValue = DEFAULT_BADGE_DISPLAY_ZERO_VALUE
    private var badgePosition = DEFAULT_BADGE_POSITION

    private var badgeColor = DEFAULT_BADGE_COLOR
    private var badgePaint: Paint? = null

    private var badgeTextColor = DEFAULT_BADGE_TEXT_COLOR
    private var badgeTextPaint: Paint? = null
    private var badgeTextSize: Int = 0

    private val badgeValueText: String
        get() {
            if (badgeValue > 0) {
                return if (badgeValue > 9) {
                    return "+9"
                } else {
                    return " " + badgeValue.toString()
                }
            } else {
                if (displayZeroValue) {
                    return " " + displayZeroValue.toString()
                }
            }
            return ""
        }

    var badgeValue: Int = DEFAULT_BADGE_VALUE
        set(value) {
            field = value
            invalidate()
        }

    constructor(context: Context) : super(context) {
        infalteView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        infalteView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        infalteView(context, attrs)
    }

    private fun infalteView(context: Context, attrs: AttributeSet?) {
        loadAttrs(context, attrs)
        badgePaint = Paint()
        badgePaint!!.isAntiAlias = true
        badgePaint!!.color = badgeColor
        badgePaint!!.style = Paint.Style.FILL

        badgeTextPaint = Paint()
        badgeTextPaint!!.isAntiAlias = true
        badgeTextPaint!!.textSize = badgeTextSize.toFloat()
        badgeTextPaint!!.color = badgeTextColor
    }

    private fun loadAttrs(context: Context, attrs: AttributeSet?) {
        val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.BadgeImageView,
                0, 0)

        try {
            displayZeroValue = a.getBoolean(R.styleable.BadgeImageView_displayZeroValue, DEFAULT_BADGE_DISPLAY_ZERO_VALUE)
            badgePosition = a.getInteger(R.styleable.BadgeImageView_badgePosition, DEFAULT_BADGE_POSITION)
            badgeColor = a.getColor(R.styleable.BadgeImageView_badgeColor, DEFAULT_BADGE_COLOR)
            badgeTextColor = a.getColor(R.styleable.BadgeImageView_badgeTextColor, DEFAULT_BADGE_TEXT_COLOR)
            badgeValue = a.getInteger(R.styleable.BadgeImageView_badgeValue, DEFAULT_BADGE_VALUE)
            badgeTextSize = a.getDimensionPixelSize(R.styleable.BadgeImageView_badgeTextSize, resources.getDimensionPixelOffset(R.dimen.badgeimageview_default_text_size))
        } finally {
            a.recycle()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val badgeRadius = height / 5

        if (!badgeValueText.isEmpty()) {

            when (badgePosition) {
                BADGE_POS_TOPLEFT -> {
                    canvas.drawCircle((0 + badgeRadius).toFloat(), (0 + badgeRadius).toFloat(), badgeRadius.toFloat(), badgePaint!!)
                    canvas.drawText(badgeValueText, (badgeRadius / 2).toFloat(), (paddingTop + badgeRadius / 2).toFloat(), badgeTextPaint!!)
                }
                BADGE_POS_TOPRIGHT -> {
                    canvas.drawCircle(width - (0 + badgeRadius).toFloat(), (0 + badgeRadius).toFloat(), badgeRadius.toFloat(), badgePaint!!)
                    canvas.drawText(badgeValueText, width - paddingRight - (badgeRadius / 2).toFloat(), (paddingTop + badgeRadius / 2).toFloat(), badgeTextPaint!!)
                }
                BADGE_POS_BOTTOMRIGHT -> {
                    canvas.drawCircle((right - badgeRadius).toFloat(), (bottom - badgeRadius).toFloat(), badgeRadius.toFloat(), badgePaint!!)
                    canvas.drawText(badgeValueText, right.toFloat(), bottom.toFloat(), badgeTextPaint!!)
                }
                BADGE_POS_BOTTOMLEFT -> {
                    canvas.drawCircle((0 + badgeRadius).toFloat(), (bottom - badgeRadius).toFloat(), badgeRadius.toFloat(), badgePaint!!)
                    canvas.drawText(badgeValueText, 0f, bottom.toFloat(), badgeTextPaint!!)
                }
            }

        }

    }

    companion object {

        val BADGE_POS_TOPLEFT = 0
        val BADGE_POS_TOPRIGHT = 1
        val BADGE_POS_BOTTOMRIGHT = 2
        val BADGE_POS_BOTTOMLEFT = 3

        val DEFAULT_BADGE_COLOR = Color.RED
        val DEFAULT_BADGE_POSITION = BADGE_POS_TOPLEFT
        val DEFAULT_BADGE_DISPLAY_ZERO_VALUE = false
        private val DEFAULT_BADGE_VALUE = 1
        private val DEFAULT_BADGE_TEXT_COLOR = Color.WHITE
    }
}
