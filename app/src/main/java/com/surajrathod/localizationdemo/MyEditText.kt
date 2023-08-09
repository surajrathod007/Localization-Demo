package com.surajrathod.localizationdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputType
import android.text.TextPaint
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText

/**
 * TODO: document your custom view class.
 */
class MyEditText : AppCompatEditText {

    lateinit var imgEye: Drawable
    var isEyeVisible = true

    interface LISTENER {
        fun onTouch(x: Int, y: Int)
    }

    var listeners = mutableListOf<LISTENER>()


    fun registerListeners(listener: LISTENER) {
        listeners.add(listener)
    }

    fun unregisterListners(listener: LISTENER) {
        listeners.remove(listener)
    }

    constructor(context: Context) : super(context) {
        initEye()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initEye()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        initEye()
    }

    private fun initEye() {
        imgEye = resources.getDrawable(R.drawable.baseline_clear_24)
        addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    if (s.isNullOrBlank()) {
                        hideEye()
                    } else {
                        showEye()
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }
            })
        setOnTouchListener(object : OnTouchListener {
            override fun onTouch(p0: View?, event: MotionEvent?): Boolean {
                if (compoundDrawablesRelative[2] != null) {
                    var clearButtonStart: Float
                    var clearButtonEnd: Float
                    var isClearButtonClicked = false

                    if (layoutDirection == LAYOUT_DIRECTION_RTL) {
                        clearButtonEnd = imgEye.intrinsicWidth + paddingStart.toFloat()
                        if (event != null) {
                            if (event.x < clearButtonEnd) {
                                isClearButtonClicked = true
                            }
                        }
                    } else {
                        clearButtonStart = (width - paddingEnd - imgEye.intrinsicWidth).toFloat()
                        if (event != null) {
                            if (event.x > clearButtonStart) {
                                isClearButtonClicked = true
                            }
                        }
                    }
                    if (isClearButtonClicked) {
                        if (event != null) {
                            if (event.action == MotionEvent.ACTION_DOWN) {
                                showEye()
                                listeners.forEach {
                                    it.onTouch(event.x.toInt(), event.y.toInt())
                                }
                                if (isEyeVisible) {
                                    inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                                    isEyeVisible = false
                                    invalidateTextPaintAndMeasurements()
                                    invalidate()
                                } else {
                                    inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                                    isEyeVisible = true
                                    invalidateTextPaintAndMeasurements()
                                    invalidate()
                                }
                            }
                            if (event.action == MotionEvent.ACTION_UP) {
                                //text?.clear()
                                //hideEye()
                                return true
                            }
                        }
                    } else {
                        return false
                    }
                }
                return false
            }
        })
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {

    }

    private fun invalidateTextPaintAndMeasurements() {

    }

    fun showEye() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, imgEye, null)
    }

    fun hideEye() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


    }
}