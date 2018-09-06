package red.itvirtuoso.ourfingering.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.support.design.widget.FloatingActionButton
import android.util.Log
import android.view.View

class FabHelper(private val context: Context, private val fab: FloatingActionButton, private val background: View, private val children: List<View>) {
    companion object {
        private val TAG = FabHelper::class.java.name
    }

    private var isClose = true

    fun toggle() {
        if (isClose) open() else close(true)
    }

    private fun open() {
        background.visibility = View.VISIBLE
        val iconWhile = 66 * context.resources.displayMetrics.density
        children.forEachIndexed { index, layout ->
            layout.visibility = View.VISIBLE
            val animator = ObjectAnimator.ofFloat(layout, "translationY", -iconWhile * (index + 1))
            animator.duration = 200
            animator.start()
        }
        val animator = ObjectAnimator.ofFloat(fab, "rotation", 45f)
        animator.duration = 200
        animator.start()
        isClose = false
    }

    fun close() {
        close(false)
    }

    private fun close(animated: Boolean) {
        val duration = if (animated) 200L else 0L
        children.forEach { layout ->
            val animator = ObjectAnimator.ofFloat(layout, "translationY", 0f)
            animator.duration = duration
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    layout.visibility = View.GONE
                }
            })
            animator.start()
        }
        val animator = ObjectAnimator.ofFloat(fab, "rotation", 0f)
        animator.duration = duration
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                background.visibility = View.GONE
            }
        })
        animator.start()
        isClose = true
    }
}