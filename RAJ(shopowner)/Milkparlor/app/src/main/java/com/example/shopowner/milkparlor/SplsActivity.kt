package com.example.shopowner.milkparlor

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.shopowner.milkparlor.R

class SplsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spls)
        Handler().postDelayed(
                Runnable {
                    finish()
                    startActivity(Intent(this, LoginActivity::class.java))
                },3000
        )
    }
}
