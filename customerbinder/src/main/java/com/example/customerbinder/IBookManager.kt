package com.example.customerbinder

import android.os.IBinder
import android.os.IInterface
import android.os.RemoteException

/**
 * Created by 全汪汪 on 2017/11/4.
 */
interface IBookManager : IInterface {
    companion object {
        val DESCRIPTOR = "com.ryg.chapter_2.manualbinder.IBookManager"
        val TRANSACTION_getBookList = IBinder.FIRST_CALL_TRANSACTION + 0
        val TRANSACTION_addBook = IBinder.FIRST_CALL_TRANSACTION + 1
    }

    @Throws(RemoteException::class)
    fun getBookList(): List<Book>

    @Throws(RemoteException::class)
    fun addBook(book: Book)
}