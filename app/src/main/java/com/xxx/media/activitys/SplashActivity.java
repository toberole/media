package com.xxx.media.activitys;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xxx.media.Constant;
import com.xxx.media.R;
import com.xxx.media.Test;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        ActivityCompat.requestPermissions(SplashActivity.this, Constant.PS, 100);
    }

    @OnClick(R.id.btn_go)
    void go() {
        gotoActivity();
    }

    private void gotoActivity() {
        Intent intent = new Intent(SplashActivity.this, Test.clazzs[Test.test_index]);
        startActivity(intent);
    }
}
