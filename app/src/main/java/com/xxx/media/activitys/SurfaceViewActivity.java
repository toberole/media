package com.xxx.media.activitys;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.AudioRecord;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.xxx.media.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * SurfaceView 绘制图形
 * 不仅可以在UI线程绘制图形
 * 还可以在一步线程中绘制图形
 */
public class SurfaceViewActivity extends AppCompatActivity {
    @BindView(R.id.surfaceView)
    SurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface_view);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(final SurfaceHolder holder) {
                // 执行绘图操作
                if (holder == null) return;
                // drawBitmap(holder);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        drawBitmap(holder);
                    }
                }).start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    private void drawBitmap(SurfaceHolder holder) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        Canvas canvas = holder.lockCanvas();
        Bitmap bitmap = BitmapFactory.decodeResource(SurfaceViewActivity.this.getResources(), R.drawable.test);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        holder.unlockCanvasAndPost(canvas);

    }
}
