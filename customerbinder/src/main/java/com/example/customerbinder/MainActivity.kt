package com.example.customerbinder

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.util.Log


class MainActivity : AppCompatActivity() {
    private lateinit var mService: Messenger

    companion object {
        val TAG = "MessengerActivity"
    }

    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder) {
            mService = Messenger(service)
            val msg = Message.obtain(null, MyConstants.MSG_FROM_CLIENT.i)
            val data = Bundle().apply { putString("msg", "hello this is client") }
            msg.data = data
            msg.replyTo = mGetReplyMessenger
            try {
                mService.send(msg)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
    }

    private val mGetReplyMessenger = Messenger(MessengerClient());

    private class MessengerClient : Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                MyConstants.MSG_FROM_SERVICE.i -> {
                    Log.i(TAG, "receive msg from Service ${msg.data.getString("reply")}")
                }
                else -> super.handleMessage(msg)
            }

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, MessengerService::class.java)
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onPause() {
        unbindService(mConnection)
        super.onPause()
    }
}
