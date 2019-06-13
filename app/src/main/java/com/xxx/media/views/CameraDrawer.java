package com.xxx.media.views;

import android.os.HandlerThread;
import android.view.SurfaceHolder;

import com.sogou.speech.base.uttils.CameraUtils;


public class CameraDrawer {
    private static final String TAG = CameraDrawer.class.getSimpleName();

    private Object mSynOperation = new Object();
    private Object mSyncIsLooping = new Object();

    private HandlerThread mHandlerThread;

    private int loopingInterval;

    private CameraDrawerHandler mDrawerHandler;
    private boolean isPreviewing;
    private boolean isRecording;

    public void surfaceCreated(SurfaceHolder holder) {
        create();
        if (mDrawerHandler != null) {
            mDrawerHandler.sendMessage(mDrawerHandler.obtainMessage(CameraDrawerHandler.MSG_SURFACE_CREATED, holder));
        }
    }

    public void surfacrChanged(int width, int height) {
        if (mDrawerHandler != null) {
            mDrawerHandler.sendMessage(mDrawerHandler
                    .obtainMessage(CameraDrawerHandler.MSG_SURFACE_CHANGED, width, height));
        }
        startPreview();
    }

    public void surfaceDestroyed() {
        stopPreview();
        if (mDrawerHandler != null) {
            mDrawerHandler.sendMessage(mDrawerHandler
                    .obtainMessage(CameraDrawerHandler.MSG_SURFACE_DESTROYED));
        }
        destroy();
    }

    /**
     * 开始预览
     */
    public void startPreview() {
        if (mDrawerHandler == null) {
            return;
        }
        synchronized (mSynOperation) {
            mDrawerHandler.sendMessage(mDrawerHandler
                    .obtainMessage(CameraDrawerHandler.MSG_START_PREVIEW));
            synchronized (mSyncIsLooping) {
                isPreviewing = true;
            }
        }
    }

    /**
     * 停止预览
     */
    public void stopPreview() {
        if (mDrawerHandler == null) {
            return;
        }
        synchronized (mSynOperation) {
            mDrawerHandler.sendMessage(mDrawerHandler
                    .obtainMessage(CameraDrawerHandler.MSG_STOP_PREVIEW));
            synchronized (mSyncIsLooping) {
                isPreviewing = false;
            }
        }
    }

    /**
     * 改变Filter类型
     */
    public void changeFilterType(FilterType type) {
        if (mDrawerHandler == null) {
            return;
        }
        synchronized (mSynOperation) {
            mDrawerHandler.sendMessage(mDrawerHandler
                    .obtainMessage(CameraDrawerHandler.MSG_FILTER_TYPE, type));
        }
    }

    /**
     * 更新预览大小
     * @param width
     * @param height
     */
    public void updatePreview(int width, int height) {
        if (mDrawerHandler == null) {
            return;
        }
        synchronized (mSynOperation) {
            mDrawerHandler.sendMessage(mDrawerHandler
                    .obtainMessage(CameraDrawerHandler.MSG_UPDATE_PREVIEW, width, height));
        }
    }

    /**
     * 开始录制
     */
    public void startRecording() {
        if (mDrawerHandler == null) {
            return;
        }
        synchronized (mSynOperation) {
            mDrawerHandler.sendMessage(mDrawerHandler
                    .obtainMessage(CameraDrawerHandler.MSG_START_RECORDING));
        }
    }

    /**
     * 停止录制
     */
    public void stopRecording() {
        if (mDrawerHandler == null) {
            return;
        }
        synchronized (mSynOperation) {
            mDrawerHandler.sendEmptyMessage(CameraDrawerHandler.MSG_STOP_RECORDING);
            synchronized (mSyncIsLooping) {
                isRecording = false;
            }
        }
    }

    /**
     * 拍照
     */
    public void takePicture() {
        if (mDrawerHandler == null) {
            return;
        }
        synchronized (mSynOperation) {
            // 发送拍照命令
            mDrawerHandler.sendMessage(mDrawerHandler
                    .obtainMessage(CameraDrawerHandler.MSG_TAKE_PICTURE));
        }
    }

    public void create() {
        mHandlerThread = new HandlerThread("CameraDrawer Thread");
        mHandlerThread.start();

        mDrawerHandler = new CameraDrawerHandler(mHandlerThread.getLooper());
        mDrawerHandler.sendEmptyMessage(CameraDrawerHandler.MSG_INIT);

        loopingInterval = CameraUtils.DESIRED_PREVIEW_FPS;
    }

    public void destroy() {
        // Handler不存在时，需要销毁当前线程，否则可能会出现重新打开不了的情况
        if (mDrawerHandler == null) {
            if (mHandlerThread != null) {
                mHandlerThread.quitSafely();
                try {
                    mHandlerThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandlerThread = null;
            }
            return;
        }

        synchronized (mSynOperation) {
            mDrawerHandler.sendEmptyMessage(CameraDrawerHandler.MSG_DESTROY);
            mHandlerThread.quitSafely();
            try {
                mHandlerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mHandlerThread = null;
            mDrawerHandler = null;
        }
    }

    public static CameraDrawer getInstance() {
        return CameraDrawerHolder.instance;
    }

    private CameraDrawer() {
    }

    private static class CameraDrawerHolder {
        public static CameraDrawer instance = new CameraDrawer();
    }
}