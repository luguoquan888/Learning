package com.example.binderpool

/**
 * Created by 全汪汪 on 2017/11/9.
 */
class ComputeImpl : ICompute.Stub() {
    override fun add(a: Int, b: Int): Int {
        return a + b
    }
}