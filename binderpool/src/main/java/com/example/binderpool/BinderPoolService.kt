package com.example.binderpool

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

/**
 * Created by 全汪汪 on 2017/11/9.
 */
class BinderPoolService : Service() {
    private val mBinderPool = BinderPool.BinderPoolImpl

    companion object {
        val TAG = "BinderPoolService"
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.d(TAG, "OnBind")
        return mBinderPool
    }
}