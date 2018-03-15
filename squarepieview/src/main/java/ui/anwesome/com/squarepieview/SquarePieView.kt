package ui.anwesome.com.squarepieview

/**
 * Created by anweshmishra on 15/03/18.
 */
import android.app.Activity
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
        val scales : Array<Float> = arrayOf(0f, 0f, 0f, 0f)
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
    data class SquarePie(var i : Int, var state : State = State()) {
        fun draw(canvas : Canvas, paint : Paint) {
            val w = canvas.width.toFloat()
            val h = canvas.height.toFloat()
            val size = Math.min(w,h)/ 5
            paint.color = Color.parseColor("#ef5350")
            paint.style = Paint.Style.STROKE
            paint.strokeCap = Paint.Cap.ROUND
            paint.strokeWidth = Math.min(w, h) / 50
            val sx = -(size/2) * state.scales[1]
            canvas.save()
            canvas.translate(w/2 , h/2)
            for(i in 0..1) {
                val sf = 1f - 2 * i
                for(i in 0..1) {
                    canvas.save()
                    canvas.translate(sx * sf, size/2 * sf)
                    canvas.rotate(90f * i * state.scales[2])
                    canvas.drawLine(0f, 0f, 0f, -size * sf * state.scales[0], paint)
                    canvas.restore()
                }
            }
            paint.style = Paint.Style.FILL
            canvas.drawArc(RectF(-size/3, -size/3, size/3, size/3), 0f, 360f * state.scales[3], true, paint)
            canvas.restore()
        }
        fun update(stopcb : (Float) -> Unit) {
            state.update(stopcb)
        }
        fun startUpdating(startcb : () -> Unit) {
            state.startUpdating(startcb)
        }
    }
    data class Renderer(var view : SquarePieView) {
        val squarePie = SquarePie(0)
        val animator = Animator(view)
        fun render(canvas : Canvas, paint : Paint) {
            canvas.drawColor(Color.parseColor("#212121"))
            squarePie.draw(canvas, paint)
            animator.animate {
                squarePie.update {
                    animator.stop()
                }
            }
        }
        fun handleTap() {
            squarePie.startUpdating {
                animator.start()
            }
        }
    }
    companion object {
        fun create(activity : Activity) : SquarePieView {
            val view = SquarePieView(activity)
            activity.setContentView(view)
            return view
        }
    }
}