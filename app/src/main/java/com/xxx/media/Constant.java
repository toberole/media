package com.xxx.media;

import android.Manifest;
import android.os.Environment;

import java.io.File;

public class Constant {
    public static final String[] PS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static final String APP_DIR = Environment.getExternalStorageDirectory() + File.separator + "a_media";

    public static int screen_w;
    public static int screen_h;
}
