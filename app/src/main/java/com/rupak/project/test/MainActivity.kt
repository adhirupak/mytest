package com.rupak.project.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rupak.project.test.ui.fragments.search.SearchFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_frame_layout,
                    SearchFragment(), "search")
                .commitNow()
        }

    }


}
