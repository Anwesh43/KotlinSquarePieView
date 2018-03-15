package ui.anwesome.com.squarepieview

/**
 * Created by anweshmishra on 15/03/18.
 */
import android.content.*
import android.view.*
import android.graphics.*
class SquarePieView(ctx : Context) : View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas : Canvas) {

    }
    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
    data class State(var prevScale : Float = 0f, var dir : Float = 0f, var j : Int = 0, var jDir : Int = 1) {
        val scales : Array<Float> = arrayOf(0f, 0f, 0f)
        fun update(stopcb : (Float) -> Unit) {
            scales[j] += 0.1f * dir
            if (Math.abs(scales[j] - prevScale) > 1) {
                scales[j] = prevScale + dir
                j += jDir
                if (dir == 0f) {
                    jDir *= -1
                    dir = 0f
                    j += jDir
                    prevScale = scales[j]
                    stopcb(scales[j])

                }
            }
        }
        fun startUpdating(startcb : () -> Unit) {
            if (dir == 0f) {
                dir = 1 - 2 * prevScale
                startcb()
            }
        }
    }
    data class Animator(var view : View, var animated : Boolean = false) {
        fun animate(updatecb : () -> Unit) {
            if (animated) {
                updatecb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch (ex : Exception) {

                }
            }
        }
        fun stop() {
            if (animated) {
                animated = false
            }
        }
        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }
    }
}