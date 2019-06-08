package com.xxx.media.business;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Connection {
    private volatile boolean isClosed = false;

    private InputStream in;
    private OutputStream out;

    public Connection(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
    }

    public synchronized void writeMsg(byte[] msg) {
        if (!isClosed && out != null) {
            try {
                out.write(msg);
            } catch (IOException e) {
                e.printStackTrace();

                close();
            }
        }
    }

    public synchronized int readMsg(byte[] res) {
        int ret = -1;
        if (!isClosed && in != null) {
            try {
                ret = in.read(res, 0, res.length);
            } catch (IOException e) {
                e.printStackTrace();
                ret = -1;
                close();
            }
        }
        return ret;
    }

    public synchronized void close() {
        if (!isClosed) {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    in = null;
                }
            }

            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    out = null;
                }
            }
        }
    }
}
