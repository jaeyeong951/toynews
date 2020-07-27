package com.toy.toynews.fcm

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class ToyNewsFirebaseMessagingService : FirebaseMessagingService() {
    private var broadcaster: LocalBroadcastManager? = null

    override fun onNewToken(s: String) {
        super.onNewToken(s)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        handleMessage(message)
        broadcaster = LocalBroadcastManager.getInstance(this)
    }

    override fun onCreate() {
        super.onCreate()

    }

    private fun handleMessage(message: RemoteMessage) {
        //1
        val handler = Handler(Looper.getMainLooper())

        //2
        handler.post {
            Log.e("LOG", "THIS IS FOREGROUND!")
            message.notification?.let {
                Log.e("LOG", "THIS IS FOREGROUND?")
                val intent = Intent("DATA")
                intent.putExtra("message", it.body)
            }
        }
    }
}