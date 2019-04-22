package com.github.zchu.common.util

import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import androidx.annotation.IntRange


fun View.removeSelfFromParent() {
    val parent = this.parent
    if (parent is ViewGroup) {
        parent.removeView(this)
    }
}

fun View.isTouchInView(ev: MotionEvent): Boolean {
    val vLoc = IntArray(2)
    getLocationOnScreen(vLoc)
    val motionX = ev.rawX
    val motionY = ev.rawY
    return motionX >= vLoc[0] && motionX <= vLoc[0] + width && motionY >= vLoc[1] && motionY <= vLoc[1] + height
}

/**
 * 获取view在屏幕中的绝对x坐标
 */
fun View.getAbsoluteX(): Float {
    var x = x
    val parent = parent
    if (parent is View) {
        x += parent.getAbsoluteX()
    }
    return x
}

/**
 * 获取view在屏幕中的绝对y坐标
 */
fun View.getAbsoluteY(): Float {
    var y = y
    val parent = parent
    if (parent is View) {
        y += parent.getAbsoluteY()
    }
    return y
}

fun <T : View> View.findViewByClass(clazz: Class<T>): T? {
    if (clazz.isAssignableFrom(this::class.java)) {
        return this as T
    }
    if (this is ViewGroup) {
        for (i in 0 until this.childCount) {
            val findViewByClass = this.getChildAt(i).findViewByClass(clazz)
            if (findViewByClass != null) {
                return findViewByClass
            }
        }

    }
    return null
}


/** Listens for item selection in a Spinner, sending the position to a callback. */
fun Spinner.onItemSelected(cb: (Int) -> Unit) {
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) = Unit

        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) = cb(position)
    }
}

/** Listens for an EditText's text changes and sends them into a callback. */
fun EditText.doOnTextChanged(
    @IntRange(from = 0, to = 10000) debounce: Int = 0,
    cb: (String) -> Unit
) {
    addTextChangedListener(object : TextWatcher {
        val callbackRunner = Runnable {
            cb(text.trim().toString())
        }

        override fun afterTextChanged(s: Editable?) = Unit

        override fun beforeTextChanged(
            s: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) = Unit

        override fun onTextChanged(
            s: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
            removeCallbacks(callbackRunner)
            if (debounce == 0) {
                callbackRunner.run()
            } else {
                postDelayed(callbackRunner, debounce.toLong())
            }
        }
    })
}


/** Sets a view's visible to [VISIBLE]. */
fun View.show() {
    visibility = VISIBLE
}

/** Sets a view's visible to [GONE]. */
fun View.hide() {
    visibility = GONE
}

/** If [show] is true, calls [show] on the receiving view, else [hide]. */
fun View.showOrHide(show: Boolean) = if (show) show() else hide()
