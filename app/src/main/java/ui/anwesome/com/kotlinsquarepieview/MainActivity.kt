package ui.anwesome.com.kotlinsquarepieview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.squarepieview.SquarePieView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SquarePieView.create(this)
    }
}
