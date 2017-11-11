package com.example.binderpool

/**
 * Created by 全汪汪 on 2017/11/9.
 */
class SecurityCenterImpl : IsecurityCenter.Stub() {
    companion object {
        val SECRET_CODE: Char = '^'
    }

    override fun encrypt(content: String?): String? {
        var chararray = content?.toCharArray()
        val charsize = chararray?.size ?: 0
        for (i in 0..charsize) {
            chararray?.set(i, SECRET_CODE)
        }
        return chararray?.let { String(it) }
    }

    override fun decrypt(password: String?): String? {
        return encrypt(password)
    }
}