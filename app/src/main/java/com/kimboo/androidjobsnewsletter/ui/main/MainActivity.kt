package com.kimboo.androidjobsnewsletter.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kimboo.androidjobsnewsletter.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.activity_main_fragment_container, MainFragment.newInstance(), MainFragment.TAG)
                    .commit()

        }
    }


}