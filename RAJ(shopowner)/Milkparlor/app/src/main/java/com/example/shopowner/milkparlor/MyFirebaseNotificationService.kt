package com.example.shopowner.milkparlor

import android.app.NotificationChannel
import android.content.Context
import android.os.Vibrator
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject
import android.app.NotificationManager
import android.graphics.Color


class MyFirebaseNotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage:  RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        Log.e("Notification","Received")
        if(remoteMessage!=null)
        {
            var data = remoteMessage!!.data
            var body = data.get("body")

            ShowNotification(body.toString())

        }
    }


    fun ShowNotification(message : String)
    {

        val NOTIFICATION_CHANNEL_ID="10001"

        var builder = NotificationCompat.Builder(this, "1001")
                .setSmallIcon(R.drawable.splash_logo)
                .setContentTitle("Milk Parlor")
                .setContentText(message)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            assert(notificationManager != null)
            builder.setChannelId(NOTIFICATION_CHANNEL_ID)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0,builder.build())
        var vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(500)

    }

    //Glide

}