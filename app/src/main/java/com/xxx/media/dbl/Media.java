package com.xxx.media.dbl;

public class Media {
    static {
        System.loadLibrary("native-lib");
    }

    public static native void playPCM(String url);

    public static native int startRecord();

    public static native int stopRecord();

    public static native void createEGL(Object surface);
}
