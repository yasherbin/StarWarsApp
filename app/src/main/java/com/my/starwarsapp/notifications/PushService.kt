package com.my.starwarsapp.notifications

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class PushService: FirebaseMessagingService() {
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.d("TAG", "Refreshed token: $p0")
    }
}