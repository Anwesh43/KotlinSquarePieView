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
}