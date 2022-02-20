package com.lenox.enjoy;

public class RustNative {

    static {
        System.loadLibrary("greeting");
    }

    public static native String greeting(String you);
}
