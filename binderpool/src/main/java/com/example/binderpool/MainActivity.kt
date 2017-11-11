package com.example.binderpool

import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.util.Log

class MainActivity : AppCompatActivity() {
    val TAG = "MainActicity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Thread(Runnable {
            val result = doWork()
            Log.d(TAG, "result: $result")
        }).start()
    }

    private fun doWork(): Int {
        val binderPool = BinderPool.getInstance(this)
        val computeBinder: IBinder = binderPool.query(BinderPool.BINDER_COMPUTE)
        val mCompute = ICompute.Stub.asInterface(computeBinder)
        val result = mCompute.add(1, 2)
        return result
    }
}
