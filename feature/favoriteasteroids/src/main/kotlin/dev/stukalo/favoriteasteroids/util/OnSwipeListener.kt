package dev.stukalo.favoriteasteroids.util

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

open class OnSwipeListener(context: Context?) : View.OnTouchListener {
    private val gestureDetector: GestureDetector

    init {
        gestureDetector = GestureDetector(context, GestureListener())
    }

    override fun onTouch(
        v: View,
        event: MotionEvent,
    ): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        private val swipeThreshold = 25
        private val swipeVelocityThreshold = 50

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(
            start: MotionEvent?,
            end: MotionEvent,
            velocityX: Float,
            velocityY: Float,
        ): Boolean {
            start?.let {
                val diffY = end.y - start.y
                val diffX = end.x - start.x
                if (abs(diffX) > abs(diffY)) {
                    if (abs(diffX) > swipeThreshold && abs(velocityX) > swipeVelocityThreshold) {
                        if (diffX < 0) {
                            onSwipeLeft()
                        } else {
                            onSwipeRight()
                        }
                        return true
                    }
                }
            }
            return false
        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            onClick()
            return super.onSingleTapUp(e)
        }
    }

    open fun onSwipeLeft() {}

    open fun onSwipeRight() {}

    open fun onClick() {}
}
