package com.xxx.media.activitys;

import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;

import com.xxx.media.R;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * TextureView 预览
 */
public class CameraTextureViewActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener {
    public static final String TAG = CameraTextureViewActivity.class.getSimpleName();

    @BindView(R.id.tv_pre_camera)
    TextureView tv_pre_camera;

    private SurfaceTexture surface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);
        tv_pre_camera.setSurfaceTextureListener(this);
    }

    private void openCamera() {
        try {
            Camera camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
            camera.setDisplayOrientation(90);
            Camera.Parameters parameters = camera.getParameters();

            List<Integer> fs = parameters.getSupportedPreviewFormats();
            for (int i = 0; i < fs.size(); i++) {
                Log.i(TAG, "Format: " + fs.get(i));
            }

            List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
            Camera.Size size = previewSizes.get(0);
            parameters.setPreviewSize(size.width, size.height);
            parameters.setPreviewFormat(ImageFormat.NV21);//NV21 the default format
            camera.setParameters(parameters);
            camera.setPreviewTexture(surface);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        this.surface = surface;
        openCamera();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        // TODO 获取预览的图片[图片格式是在parameters.setPreviewFormat设置的]
        // tv_pre_camera.getBitmap()
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}
