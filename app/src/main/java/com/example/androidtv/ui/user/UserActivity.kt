package com.example.androidtv.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.androidtv.R

/**
 * Loads [MainFragment].
 */
class UserActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val fragment: Fragment = UserFragment()
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment)
                .commit()
        }
    }
}
