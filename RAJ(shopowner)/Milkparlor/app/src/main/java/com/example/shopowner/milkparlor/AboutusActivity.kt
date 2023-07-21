package com.example.shopowner.milkparlor

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.example.shopowner.milkparlor.R
import kotlinx.android.synthetic.main.activity_aboutus.*

class AboutusActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aboutus)
       // setSupportActionBar(toolbar)
        supportActionBar?.setTitle("Terms and Conditions")
      /*  fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }*/
    }
}
