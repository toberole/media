package com.xxx.media.activitys;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.xxx.media.Constant;
import com.xxx.media.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements Camera.PreviewCallback, View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();

    private boolean isOpen = false;

    int width = 1920;
    int height = 1080;

    private Camera camera;
    private SurfaceHolder surfaceHolder;
    private SurfaceView surfaceView;
    private Button btn_swi;

    private void openCamera() {
        try {
            camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
            camera.setDisplayOrientation(90);

            Camera.Parameters parameters = camera.getParameters();
            parameters.setPreviewSize(width, height);
            parameters.setPreviewFormat(ImageFormat.NV21);
            camera.setParameters(parameters);

            camera.setPreviewDisplay(surfaceHolder);
            camera.setPreviewCallback(this);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopCamera() {
        if (camera != null) {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(MainActivity.this, Constant.PS, 110);
        surfaceView = findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        btn_swi = findViewById(R.id.btn_swi);
        btn_swi.setOnClickListener(this);
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Log.i(TAG, "onPreviewFrame");
        // saveJpegImage(data);
    }

    @Override
    public void onClick(View v) {
        if (isOpen) {
            stopCamera();
        } else {
            openCamera();
        }

        isOpen = !isOpen;
        btn_swi.setText(isOpen ? "停止预览" : "开始预览");
    }

    private void saveJpegImage(byte[] data) {
        YuvImage yuvImage = new YuvImage(data, ImageFormat.NV21, width, height, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(new Rect(0, 0, width, height), 100, baos);
        byte[] bs = baos.toByteArray();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeByteArray(bs, 0, bs.length, options);
    }
}
