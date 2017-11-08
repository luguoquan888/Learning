package com.example.customerbinder

import android.os.RemoteException

/**
 * Created by 全汪汪 on 2017/11/4.
 */

interface IBM {
    @Throws(RemoteException::class)
    fun addBook()
}
