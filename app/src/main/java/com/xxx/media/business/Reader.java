package com.xxx.media.business;

public class Reader implements Runnable {
    private Connection conn;

    public Reader(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1024];

        while (true) {
            int len = conn.readMsg(buffer);
            if (len == -1) break;
        }
    }
}
