package com.example.myfastcampusstudy_android.upper_intermediate.youtube

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.myfastcampusstudy_android.R

class CustomMotionLayout(context: Context, attributeSet: AttributeSet? = null) :
    MotionLayout(context, attributeSet) {

    private val hitRect = Rect()
    private var motionTouchStarted = false
    private val mainContainerView by lazy {
        findViewById<View>(R.id.mainContainer)
    }

    init {
        setTransitionListener(object : TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                motionTouchStarted = false
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
            }
        })
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                motionTouchStarted = false
                return super.onTouchEvent(event)
            }
        }

        if (!motionTouchStarted) {
            mainContainerView.getHitRect(hitRect)
            motionTouchStarted = hitRect.contains(event.x.toInt(), event.y.toInt())
        }


        return super.onTouchEvent(event) && motionTouchStarted
    }

    private val gestureListener by lazy {
        object : GestureDetector.SimpleOnGestureListener() {
            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                mainContainerView.getHitRect(hitRect)
                return hitRect.contains(e1.x.toInt(), e1.y.toInt())
//                return super.onScroll(e1, e2, distanceX, distanceY)
            }
        }
    }

    private val gestureDetector by lazy {
        GestureDetector(context, gestureListener)
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
//        return super.onInterceptTouchEvent(event)
    }
}