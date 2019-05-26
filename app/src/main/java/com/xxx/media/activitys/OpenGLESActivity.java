package com.xxx.media.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.xxx.media.R;
import com.xxx.media.gl.MyGLSurfaceView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class OpenGLESActivity extends AppCompatActivity {
    @BindView(R.id.gl_SurfaceView)
    MyGLSurfaceView gl_SurfaceView;

    @BindView(R.id.btn_op)
    Button btn_op;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_gles);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.gl_SurfaceView)
    void gl_SurfaceView() {

    }

    @OnClick(R.id.btn_op)
    void btn_op() {

    }
}
