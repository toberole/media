package com.xxx.media.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.xxx.media.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/*
    MediaExtractor的作用是把音频和视频的数据进行分离。

    setDataSource(String path)：即可以设置本地文件又可以设置网络文件
    getTrackCount()：得到源文件通道数
    getTrackFormat(int index)：获取指定（index）的通道格式
    getSampleTime()：返回当前的时间戳
    readSampleData(ByteBuffer byteBuf, int offset)：把指定通道中的数据按偏移量读取到ByteBuffer中；
    advance()：读取下一帧数据
    release(): 读取结束后释放资源
 */
public class MediaExtractorActivity extends AppCompatActivity {
    @BindView(R.id.btn_mediaExtractor)
    Button btn_mediaExtractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_extractor);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_mediaExtractor)
    void btn_mediaExtractor() {

    }
}
