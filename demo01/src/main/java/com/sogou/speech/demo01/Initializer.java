package com.sogou.speech.demo01;

import android.content.Context;

import com.sogou.speech.base.uttils.AppUtil;

public class Initializer {
    public static void init(Context context) {
        AppUtil.getInstance().init(context);
    }
}
