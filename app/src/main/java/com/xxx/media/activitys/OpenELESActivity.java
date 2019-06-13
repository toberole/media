package com.xxx.media.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.xxx.media.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class OpenELESActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_eles);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_create_egl)
    void create_egl() {

    }
}
