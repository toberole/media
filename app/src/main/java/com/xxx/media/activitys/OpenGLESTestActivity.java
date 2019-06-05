package com.xxx.media.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.xxx.media.R;
import com.xxx.media.dbl.Media;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OpenGLESTestActivity extends AppCompatActivity {
    public static final String TAG = "jni-log";
    @BindView(R.id.btn_create_egl)
    Button btn_create_egl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_glestest);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_create_egl)
    void create_egl() {
        Log.i(TAG, "app create_egl");

        Media.createEGL(null);
    }
}
