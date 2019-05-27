package com.xxx.media.activitys;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.xxx.media.Constant;
import com.xxx.media.R;
import com.xxx.media.uttils.CameraUtils;
import com.xxx.media.uttils.ImageUtils;
import com.xxx.media.views.CameraSurfaceView;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraSurfaceViewActivity extends NoTitlebarActivity implements Camera.PictureCallback, Camera.ShutterCallback {
    public static final String TAG = CameraSurfaceViewActivity.class.getCanonicalName();

    @BindView(R.id.cameraSurfaceView)
    CameraSurfaceView cameraSurfaceView;

    @BindView(R.id.tv_take_picture)
    TextView tv_take_picture;

    @BindView(R.id.tv_sw_camera)
    TextView tv_sw_camera;

    private int mOrientation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_surface_view);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mOrientation = CameraUtils.calculateCameraPreviewOrientation(CameraSurfaceViewActivity.this);
        Log.i(TAG, "Orientation: " + mOrientation);
    }

    @OnClick(R.id.tv_take_picture)
    void take_picture() {
        CameraUtils.takePicture(null/* null 可以取消掉拍照瞬间 "卡卡"的声音 */, this, this);
    }

    @Override
    /**
     * Camera.PictureCallback
     */
    public void onPictureTaken(byte[] data, Camera camera) {
        Log.i(TAG, "onPictureTaken time: " + System.currentTimeMillis());

        if (data == null || data.length == 0) {
            Log.i(TAG, "data: " + String.valueOf(data));
            return;
        }

        //CameraUtils.startPreview();
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        if (bitmap != null) {
            try {
                // 注意图片的相机抓去的图片的方向与相机预览的方向
                bitmap = ImageUtils.getRotatedBitmap(bitmap, -mOrientation);
                String path = Constant.APP_PIC_DIR + "/"
                        + System.currentTimeMillis() + ".jpg";
                FileOutputStream fout = new FileOutputStream(path);
                BufferedOutputStream bos = new BufferedOutputStream(fout);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                bos.flush();
                bos.close();
                fout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        CameraUtils.startPreview();
    }

    @Override
    /**
     * Camera.ShutterCallback
     */
    public void onShutter() {
        Log.i(TAG, "----- onShutter -----");
    }
}
