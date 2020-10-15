package com.example.androidtv

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * Loads [MainFragment].
 */
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val fragment: Fragment = MainFragment2()
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment)
                .commit()
        }
    }
}
