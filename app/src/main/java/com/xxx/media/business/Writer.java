package com.xxx.media.business;

public class Writer implements Runnable {
    private Connection conn;

    public Writer(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void run() {
        while (true) {
            // TODO getMsg for send
        }
    }

    public void sendMsg(byte[] data) {
        conn.writeMsg(data);
    }
}
