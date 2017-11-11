// IsecurityCenter.aidl
package com.example.binderpool;

// Declare any non-default types here with import statements

interface IsecurityCenter {
    String encrypt(String content);
    String decrypt(String psaaword);
}
