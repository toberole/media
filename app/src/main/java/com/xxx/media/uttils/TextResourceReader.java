package com.xxx.media.uttils;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class TextResourceReader {
    public static String readTextFileFromResource(Context context, int id) {
        String res = null;
        try {
            InputStream in = context.getResources().openRawResource(id);
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer, 0, buffer.length)) > 0) {
                out.write(buffer, 0, len);
            }

            res = new String(out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            res = null;
        }
        return res;
    }
}
