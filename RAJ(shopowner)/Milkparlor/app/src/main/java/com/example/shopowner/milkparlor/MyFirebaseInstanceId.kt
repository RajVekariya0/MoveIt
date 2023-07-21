package com.example.shopowner.milkparlor

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class MyFirebaseInstanceId : FirebaseInstanceIdService()
{

    override fun onTokenRefresh() {
        super.onTokenRefresh()
        try {
            var refreshToken = FirebaseInstanceId.getInstance().getToken().toString()
            Log.e("Refresh Token",refreshToken)
        }
        catch (e:Exception)
        {
            Log.e("Token","Error",e)
        }
    }

}