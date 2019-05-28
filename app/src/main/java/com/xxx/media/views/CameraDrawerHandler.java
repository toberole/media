package com.xxx.media.views;


import android.graphics.SurfaceTexture;
import android.opengl.GLES30;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.SurfaceHolder;

import com.xxx.media.uttils.CameraUtils;

public class CameraDrawerHandler extends Handler implements SurfaceTexture.OnFrameAvailableListener {
    public static final String TAG = CameraDrawerHandler.class.getSimpleName();

    public static final int MSG_INIT = 1;
    public static final int MSG_DESTROY = 2;
    public static final int MSG_SURFACE_CREATED = 3;
    public static final int MSG_SURFACE_CHANGED = 4;
    public static final int MSG_SURFACE_DESTROYED = 5;
    public static final int MSG_START_PREVIEW = 6;
    public static final int MSG_STOP_PREVIEW = 7;
    public static final int MSG_FILTER_TYPE = 8;
    public static final int MSG_UPDATE_PREVIEW = 9;
    public static final int MSG_START_RECORDING = 10;
    public static final int MSG_STOP_RECORDING = 11;
    public static final int MSG_TAKE_PICTURE = 12;
    private static final int MSG_RESET_BITRATE = 13;
    private static final int MSG_FRAME = 14;
    private static final int MSG_RESET = 15;

    // EGL共享上下文
    private EglCore mEglCore;
    // EGLSurface
    private WindowSurface mDisplaySurface;

    // CameraTexture对应的Id
    private int mTextureId;
    private SurfaceTexture mCameraTexture;

    // 矩阵
    private final float[] mMatrix = new float[16];
    // 视图宽高
    private int mViewWidth, mViewHeight;
    // 预览图片大小
    private int mImageWidth, mImageHeight;

    // 更新帧的锁
    private final Object mSyncFrameNum = new Object();
    private final Object mSyncTexture = new Object();

    private int mFrameNum = 0;
    private boolean hasNewFrame = false;
    public boolean dropNextFrame = false;
    private boolean isTakePicture = false;
    private boolean mSaveFrame = false;
    private int mSkipFrame = 0;

    private CameraFilter mCameraFilter;
    private BaseImageFilter mFilter;

    private Object mSyncIsLooping = new Object();

    public CameraDrawerHandler(Looper looper) {
        super(looper);
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_INIT:// 初始化
                break;
            case MSG_DESTROY:// 销毁
                break;
            case MSG_SURFACE_CREATED:// surfacecreated
                onSurfaceCreated((SurfaceHolder) msg.obj);
                break;
            case MSG_SURFACE_CHANGED:// surfaceChanged
                onSurfaceChanged(msg.arg1, msg.arg2);
                break;
            case MSG_SURFACE_DESTROYED:// surfaceDestroyed;
                onSurfaceDestoryed();
                break;
            case MSG_FRAME:// 帧可用（考虑同步的问题）
                drawFrame();
                break;
            case MSG_FILTER_TYPE:
                setFilter((FilterType) msg.obj);
                break;
            case MSG_RESET:// 重置
                break;
            case MSG_START_PREVIEW:// 开始预览
                break;
            case MSG_STOP_PREVIEW:// 停止预览
                break;
            case MSG_UPDATE_PREVIEW:// 更新预览大小
                synchronized (mSyncIsLooping) {
                    mViewWidth = msg.arg1;
                    mViewHeight = msg.arg2;
                }
                break;
            case MSG_START_RECORDING:// 开始录制
                break;
            case MSG_STOP_RECORDING:// 停止录制
                break;
            // 重置bitrate(录制视频时使用)
            case MSG_RESET_BITRATE:
                break;
            case MSG_TAKE_PICTURE:// 拍照
                isTakePicture = true;
                break;
            default:
                throw new IllegalStateException("Can not handle message what is: " + msg.what);
        }
    }

    private void setFilter(FilterType filterType) {

    }

    /**
     * 绘制帧
     */
    private void drawFrame() {
        mDisplaySurface.makeCurrent();
        synchronized (mSyncFrameNum) {
            synchronized (mSyncTexture) {
                if (mCameraTexture != null) {
                    // 如果存在新的帧，则更新帧
                    while (mFrameNum != 0) {
                        mCameraTexture.updateTexImage();
                        --mFrameNum;
                        // 是否舍弃下一帧
                        if (!dropNextFrame) {
                            hasNewFrame = true;
                        } else {
                            dropNextFrame = false;
                            hasNewFrame = false;
                        }
                    }
                } else {
                    return;
                }
            }
        }
        mCameraTexture.getTransformMatrix(mMatrix);
        if (mFilter == null) {
            mCameraFilter.drawFrame(mTextureId, mMatrix);
        } else {
            int id = mCameraFilter.drawToTexture(mTextureId, mMatrix);
            mFilter.drawFrame(id, mMatrix);
        }
        mDisplaySurface.swapBuffers();
    }

    /**
     * 添加新的一帧
     */
    public void addNewFrame() {
        synchronized (mSyncFrameNum) {
            ++mFrameNum;
            removeMessages(MSG_FRAME);
            sendMessageAtFrontOfQueue(obtainMessage(MSG_FRAME));
        }
    }

    private void onSurfaceDestoryed() {

    }

    private void onSurfaceChanged(int width, int height) {
        mViewWidth = width;
        mViewHeight = height;
        onFilterChanged();
        CameraUtils.startPreviewTexture(mCameraTexture);
    }

    /**
     * 滤镜或视图发生变化时调用
     */
    private void onFilterChanged() {
        mCameraFilter.onDisplayChanged(mViewWidth, mViewHeight);
        if (mFilter != null) {
            mCameraFilter.initCameraFramebuffer(mImageWidth, mImageHeight);
        } else {
            mCameraFilter.destroyFramebuffer();
        }
    }

    private void onSurfaceCreated(SurfaceHolder holder) {
        mEglCore = new EglCore(null, EglCore.FLAG_RECORDABLE);
        mDisplaySurface = new WindowSurface(mEglCore, holder.getSurface(), false);
        mDisplaySurface.makeCurrent();
        if (mCameraFilter == null) {
            mCameraFilter = new CameraFilter();
        }
        mTextureId = createTextureOES();
        mCameraTexture = new SurfaceTexture(mTextureId);
        mCameraTexture.setOnFrameAvailableListener(CameraDrawerHandler.this);
        CameraUtils.openFrontalCamera(CameraUtils.DESIRED_PREVIEW_FPS);
        calculateImageSize();
        mCameraFilter.onInputSizeChanged(mImageWidth, mImageHeight);
        mFilter = FilterManager.getFilter(FilterType.SATURATION);

        // 禁用深度测试和背面绘制
        GLES30.glDisable(GLES30.GL_DEPTH_TEST);
        GLES30.glDisable(GLES30.GL_CULL_FACE);
    }

    private void calculateImageSize() {

    }

    private int createTextureOES() {
        return 0;
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {

    }
}
