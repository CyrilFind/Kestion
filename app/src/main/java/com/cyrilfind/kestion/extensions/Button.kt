package com.cyrilfind.kestion.extensions

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.support.v4.content.ContextCompat
import android.widget.Button
import com.cyrilfind.kestion.R

fun Button.flash(colorResId: Int, callback: () -> Unit = {}) {
    val animator = ObjectAnimator.ofObject(
        background,
        "tint",
        ArgbEvaluator(),
        ContextCompat.getColor(context, R.color.colorPrimary),
        ContextCompat.getColor(context, colorResId)
    )
    animator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {}

        override fun onAnimationEnd(animation: Animator) {
            callback()
        }

        override fun onAnimationCancel(animation: Animator) {}

        override fun onAnimationRepeat(animation: Animator) {}
    })
    animator.repeatCount = 1
    animator.repeatMode = ValueAnimator.REVERSE
    animator.duration = 300
    animator.start()
}