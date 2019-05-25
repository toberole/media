package com.xxx.media.uttils;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class MQ {
    protected BlockingDeque<Object> datas = new LinkedBlockingDeque<>();

    public synchronized void clear() {
        datas.clear();
    }

    public synchronized int size() {
        return datas.size();
    }

    public synchronized void offer(Object data) {
        if (data != null) {
            datas.offer(data);
        }
    }

    public synchronized <T> T take() {
        try {
            return (T) datas.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public synchronized <T> T poll() {
        return (T) datas.poll();
    }

    public synchronized <T> T poll(int timeOut) {
        try {
            return (T) datas.poll(timeOut, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
