package com.example.androidtv.ui.device

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.androidtv.R

/**
 * Loads [MainFragment].
 */
class DeviceActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val fragment: Fragment = DeviceFragment()
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment)
                .commit()
        }
    }
}
