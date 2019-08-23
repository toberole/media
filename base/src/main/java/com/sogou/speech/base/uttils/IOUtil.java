package com.sogou.speech.base.uttils;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public class IOUtil {
    public static String stream2Str(InputStream inputStream) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len = 0;
        byte[] buffer = new byte[1024];
        try {
            while ((len = inputStream.read(buffer, 0, buffer.length)) > 0) {
                bos.write(buffer, 0, len);
                bos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new String(bos.toByteArray());
    }

    public static String readTextFromResource(Context context, int id) {
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

    public static void closeQuietly(Closeable... closeable) {
        if (closeable != null) {
            for (Closeable c : closeable) {
                try {
                    if (null != c) {
                        c.close();
                    }
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }
}
