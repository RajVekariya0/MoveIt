package com.example.shopowner.milkparlor

import android.app.Application
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class VolleyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        instance =this
    }


    companion object {
        private val TAG= VolleyApplication::class.java.name
        @get:Synchronized var instance: VolleyApplication?=null

        private set
    }

    fun <T> addRequestQueue(request: Request<T>)
    {
        request.tag= TAG
        var retryPolicy=DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

        request.setRetryPolicy(retryPolicy)
        requestQueue?.add(request )




    }

    var requestQueue:RequestQueue?=null
        get()
        {
            if(field==null)
            {
                return Volley.newRequestQueue(applicationContext)
            }
            return field
        }
}