package com.example.binderpool

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import java.util.concurrent.CountDownLatch

/**
 * Created by 全汪汪 on 2017/11/9.
 */
class BinderPool {
    private var mContext: Context
    private lateinit var mBinderPool: IBinderPool
    private lateinit var mConnectBinderPoolCountDownLatch: CountDownLatch

    private constructor(context: Context) {
        mContext = context.applicationContext
        connectBinderPoolService()
    }

    companion object {
        val TAG = "BinderPool"
        val BINDER_NONE = -1
        val BINDER_COMPUTE = 0
        val BINDER_SECURITY_CENTER = 1
        private @Volatile lateinit var sInstance: BinderPool

        fun getInstance(context: Context): BinderPool {
            return Instance.getInstance(context)
        }

        val BinderPoolImpl: IBinderPool.Stub = object : IBinderPool.Stub() {
            override fun queryBinder(binderCode: Int): IBinder {
                val binder: IBinder = when (binderCode) {
                    BINDER_SECURITY_CENTER -> SecurityCenterImpl()
                    BINDER_COMPUTE -> ComputeImpl()
                    else -> ComputeImpl() //default
                }
                return binder
            }
        }
    }

    private object Instance {
        fun getInstance(context: Context): BinderPool {
            sInstance = BinderPool(context)
            return sInstance
        }
    }

    private val mConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mBinderPool = IBinderPool.Stub.asInterface(service)
            mConnectBinderPoolCountDownLatch.countDown()
        }

    }

    private @Synchronized
    fun connectBinderPoolService() {
        mConnectBinderPoolCountDownLatch = CountDownLatch(1)
        val intent = Intent(mContext, BinderPoolService::class.java)
        mContext.bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
        try {
            mConnectBinderPoolCountDownLatch.await()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun query(code: Int): IBinder {
        return mBinderPool?.queryBinder(code)
    }

}