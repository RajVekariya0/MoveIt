package com.example.shopowner.milkparlor

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.shopowner.milkparlor.R
import kotlinx.android.synthetic.main.app_bar_dashboard.*

class ContectusActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contectus)
        supportActionBar?.setTitle("Contact Us")
    }
}
