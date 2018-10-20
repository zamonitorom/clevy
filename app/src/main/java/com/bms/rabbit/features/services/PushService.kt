package com.bms.rabbit.features.services

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.bms.rabbit.R
import com.bms.rabbit.features.main.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

// Created by Konstantin on 20.10.2018.
//{
//    'data': {
//    'screen': 'lesson',
//    'lesson_id': 12,
//    'title': 'Title',
//    'text': 'Text'
//}
//}
class PushService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("onMessageReceived", "From: " + remoteMessage.from)
        if (remoteMessage.data != null && remoteMessage.data.isNotEmpty()) {
            Log.d("onMessageReceived", "Message data payload: " + remoteMessage.data)
            sendNotification(remoteMessage.data)
        }
//        sendNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("body"));
    }

    private fun sendNotification(valueMap: Map<String, String>) {
        val screen = valueMap["screen"]
        val lesson_id: Int = if (valueMap.containsKey("lesson_id")) valueMap["lesson_id"]!!.toInt() else 0
        val title = valueMap["title"]
        val text = valueMap["text"]

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("screen", screen)
        intent.putExtra("id", lesson_id)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT)
        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.clevy_logo_2)
        val soundLink = Uri.parse("android.resource://" + packageName + "/" + R.raw.income_message)
        val noti = Notification.Builder(this)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.clevy_logo_on_push)
                .setLargeIcon(largeIcon)
                .setSound(soundLink)
                .setContentIntent(pendingIntent).getNotification()

        noti.flags = Notification.FLAG_AUTO_CANCEL

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (notificationManager != null) {
            notificationManager.notify(0, noti)
        } else {
            Log.d("null", "can not push notify")
        }
    }

}