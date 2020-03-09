package com.rupak.project.test.ui.main.activity

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.rupak.project.test.R
import com.rupak.project.test.ui.main.fragments.search.SearchFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.main_frame_layout,
                    SearchFragment(), "search")
                .commitNow()
        }

    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val view: View? = currentFocus
        val ret = super.dispatchTouchEvent(event)
        if (view is EditText) {
            val w: View? = currentFocus
            val scrcoords = IntArray(2)
            w!!.getLocationOnScreen(scrcoords)
            val x: Float = event.rawX + w!!.getLeft() - scrcoords[0]
            val y: Float = event.rawY + w!!.getTop() - scrcoords[1]
            if (event.action == MotionEvent.ACTION_UP
                && (x < w!!.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom())
            ) {
                val imm: InputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(window.currentFocus!!.windowToken, 0)
            }
        }
        return ret
    }


}
