package com.xxx.media.tasks;

import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.util.Log;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * 音视频分离
 */
public class MediaExtractorTask implements Runnable {
    public static final String TAG = MediaExtractorTask.class.getSimpleName();

    private String media_path;

    public MediaExtractorTask(String media_path) {
        this.media_path = media_path;
    }

    @Override
    public void run() {
        try {
            MediaExtractor extractor = new MediaExtractor();
            extractor.setDataSource(media_path);
            int trackCount = extractor.getTrackCount();
            Log.i(TAG, "trackCount: " + trackCount);

            for (int i = 0; i < trackCount; i++) {
                MediaFormat mediaFormat = extractor.getTrackFormat(i);
                String mime = mediaFormat.getString(MediaFormat.KEY_MIME);
                Log.i(TAG, "mime: " + mime);
            }
            // 选取需要读取的track
            // extractor.selectTrack(i);
            // 读取数据
            // ByteBuffer buffer = ByteBuffer.allocate(capacity);
            // extractor.readSampleData(buffer)
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
