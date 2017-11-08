package com.example.customerbinder

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log

/**
 * Created by 全汪汪 on 2017/11/4.
 */
class MessengerService : Service() {
    companion object {
        val TAG = "MessengerService"

        private class MessengerHandler : Handler() {
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    MyConstants.MSG_FROM_CLIENT.i -> {
                        Log.i(TAG, "receiver msg from client: ${msg.data.getString("msg")}")
                        val messenger = msg.replyTo
                        val message = Message.obtain(null, MyConstants.MSG_FROM_SERVICE.i)
                        val data = Bundle().apply { putString("reply", "reply from Service") }
                        message.data = data
                        messenger.send(message)
                    }
                    else -> {
                        super.handleMessage(msg)
                    }
                }
            }
        }
    }

    private val mMessenger = Messenger(MessengerHandler())

    override fun onBind(intent: Intent?): IBinder {
        return mMessenger.binder
    }
}