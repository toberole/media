package com.xxx.media.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.sogou.speech.base.uttils.AppUtil;
import com.xxx.media.R;
import com.xxx.media.tasks.MediaMuxerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MediaMuxerActivity extends AppCompatActivity {
    @BindView(R.id.btn_MediaMuxer)
    Button btn_MediaMuxer;

    public static final String PATH = "/sdcard/a_media/2019_05_25_15_25_27.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_muxer);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_MediaMuxer)
    void btn_MediaMuxer() {
        MediaMuxerTask mediaMuxerTask = new MediaMuxerTask(PATH);
        AppUtil.getInstance().execute(mediaMuxerTask);
    }
}
