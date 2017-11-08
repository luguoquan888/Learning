package com.example.customerbinder

import android.os.Binder
import android.os.IBinder
import android.os.IInterface
import android.os.Parcel

/**
 * Created by 全汪汪 on 2017/11/4.
 */
class BookManagerImpl : Binder(), IBookManager {
    init {
        this.attachInterface(this, IBookManager.DESCRIPTOR)
    }

    companion object {
        private class Proxy(obj: IBinder) : IBookManager {
            private var mRemote: IBinder

            init {
                mRemote = obj
            }

            override fun getBookList(): List<Book> {
                val data = Parcel.obtain()
                val reply = Parcel.obtain()
                val result: List<Book>
                try {
                    data.enforceInterface(IBookManager.DESCRIPTOR)
                    mRemote.transact(IBookManager.TRANSACTION_getBookList, data, reply, 0)
                    reply.readException()
                    result = reply.createTypedArrayList(Book.CREATOR)
                } finally {
                    reply.recycle()
                    data.recycle()
                }
                return result
            }

            override fun addBook(book: Book) {
                val data = Parcel.obtain()
                val reply = Parcel.obtain()
                try {
                    data.writeInterfaceToken(IBookManager.DESCRIPTOR)
                    data.writeInt(1)
                    book.writeToParcel(data, 0)
                    mRemote.transact(IBookManager.TRANSACTION_addBook, data, reply, 0)
                    reply.readException()
                } finally {
                    reply.recycle()
                    data.recycle()
                }
            }

            override fun asBinder(): IBinder {
                return mRemote
            }

            fun getInterfaceDescriptor(): String {
                return IBookManager.DESCRIPTOR
            }

        }

        fun asInterface(obj: IBinder): IBookManager {
            var iin: IInterface = obj.queryLocalInterface(IBookManager.DESCRIPTOR)
            if (iin != null && (iin is IBookManager)) {
                return iin
            }
            return Proxy(obj)
        }
    }

    override fun asBinder(): IBinder {
        return this
    }

    override fun addBook(book: Book) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBookList(): List<Book> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
        when (code) {
            IBinder.INTERFACE_TRANSACTION -> {
                reply?.writeString(IBookManager.DESCRIPTOR)
                return true
            }
            IBookManager.TRANSACTION_getBookList -> {
                data.enforceInterface(IBookManager.DESCRIPTOR)
                var result: List<Book> = this.getBookList()
                reply?.writeNoException()
                reply?.writeTypedList(result)
                return true
            }
            IBookManager.TRANSACTION_addBook -> {
                data.enforceInterface(IBookManager.DESCRIPTOR)
                var arg0: Book
                arg0 = Book.createFromParcel(data)
                this.addBook(arg0)
                reply?.writeNoException()
                return true
            }
        }
        return super.onTransact(code, data, reply, flags)
    }

}