package red.itvirtuoso.ourfingering.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import red.itvirtuoso.ourfingering.R

class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = MainActivity::class.java.name
    }

    private var mIsClose = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            val floats = listOf(fab_camera_layout, fab_library_layout)
            if (mIsClose) {
                fabOpen(floats)
                mIsClose = false
            } else {
                fabClose(floats)
                mIsClose = true
            }
        }
    }

    private fun fabOpen(floats: List<LinearLayout>) {
        fab_background.visibility = View.VISIBLE
        val iconWhile = 66 * applicationContext.resources.displayMetrics.density
        floats.forEachIndexed { index, layout ->
            layout.visibility = View.VISIBLE
            val animator = ObjectAnimator.ofFloat(layout, "translationY", -iconWhile * (index + 1))
            animator.duration = 200
            animator.start()
        }
        val animator = ObjectAnimator.ofFloat(fab, "rotation", 45f)
        animator.duration = 200
        animator.start()
    }

    private fun fabClose(floats: List<LinearLayout>) {
        floats.forEach { layout ->
            val animator = ObjectAnimator.ofFloat(layout, "translationY", 0f)
            animator.duration = 200
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    layout.visibility = View.GONE
                }
            })
            animator.start()
        }
        val animator = ObjectAnimator.ofFloat(fab, "rotation", 0f)
        animator.duration = 200
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                fab_background.visibility = View.GONE
            }
        })
        animator.start()
    }
}
