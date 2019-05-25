package com.xxx.media.uttils;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.view.View;

public class AntiShakeUtils {
    private static final long INTERNAL_TIME = 1500;

    public static boolean isInvalidClick(@NonNull View target) {
        return isInvalidClick(target, INTERNAL_TIME);
    }

    public static boolean isInvalidClick(@NonNull View target, @IntRange(from = 0) long internalTime) {
        long curTimeStamp = System.currentTimeMillis();
        long lastClickTimeStamp = 0;
        Object o = target.getTag();
        if (o == null){
            target.setTag(curTimeStamp);
            return false;
        }
        lastClickTimeStamp = (Long) o;
        boolean isInvalid = curTimeStamp - lastClickTimeStamp < internalTime;
        if (!isInvalid)
            target.setTag(curTimeStamp);
        return isInvalid;
    }
}