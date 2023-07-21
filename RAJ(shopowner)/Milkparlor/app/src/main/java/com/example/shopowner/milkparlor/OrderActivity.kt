package com.example.shopowner.milkparlor

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SimpleAdapter
import kotlinx.android.synthetic.main.activity_order.*
import org.json.JSONArray

class OrderActivity : AppCompatActivity() {
    lateinit var myPref: MyPref
    var appConfig=appconfig()
    var orderDetails=JSONArray()
    var bname=ArrayList<HashMap<String,String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        appConfig = appconfig()
        myPref= MyPref(this)

        var bundle = intent.extras
        orderDetails= JSONArray(bundle.getString("details"))
        rvitem.adapter=itemadapter(this,orderDetails)

        Log.e("Order Details",orderDetails.toString())

    }



}





