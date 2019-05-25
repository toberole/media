package com.xxx.media.tasks;


import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.util.Log;

import com.xxx.media.Constant;

import java.io.IOException;
import java.nio.ByteBuffer;

/*
    MediaMuxer的作用是生成音频或视频文件；还可以把音频与视频混合成一个音视频文件。
    // path:输出文件的名称  format:输出文件的格式；当前只支持MP4格式；
    MediaMuxer(String path, int format)

    // 添加通道 更多的是使用MediaCodec.getOutpurForma()或Extractor.getTrackFormat(int index)来获取MediaFormat;也可以自己创建；
    addTrack(MediaFormat format)
    start()：开始合成文件
    writeSampleData(int trackIndex, ByteBuffer byteBuf, MediaCodec.BufferInfo bufferInfo)：把ByteBuffer中的数据写入到在构造器设置的文件中；
    stop()：停止合成文件
    release()：释放资源
 */
public class MediaMuxerTask implements Runnable {
    public static final String TAG = MediaMuxerTask.class.getSimpleName();

    private String path;
    private String targetPath;
    private MediaMuxer mediaMuxer;
    private int video_track;

    public MediaMuxerTask(String path) {
        this.path = path;
    }

    @Override
    public void run() {
        // 从一个文件中提取视频数据 合成一个新的文件
        try {
            MediaExtractor mediaExtractor = new MediaExtractor();
            mediaExtractor.setDataSource(path);
            int trackCount = mediaExtractor.getTrackCount();

            int targetIndex = -1;
            String targetMime = "video";

            int frame_rate = 1;

            for (int i = 0; i < trackCount; i++) {
                MediaFormat mediaFormat = mediaExtractor.getTrackFormat(i);
                if (mediaFormat == null) continue;
                String mime = mediaFormat.getString(MediaFormat.KEY_MIME);
                Log.i(TAG, "mime: " + mime);
                if (mime.startsWith(targetMime)) {
                    targetIndex = i;
                    // 获取原视频的帧率
                    frame_rate = mediaFormat.getInteger(MediaFormat.KEY_FRAME_RATE);
                    targetPath = Constant.APP_DIR + "/" + System.currentTimeMillis() + ".mp4";
                    // 合成为mp4文件
                    mediaMuxer = new MediaMuxer(targetPath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
                    video_track = mediaMuxer.addTrack(mediaFormat);
                    mediaMuxer.start();
                    break;
                }
            }

            if (targetIndex == -1) return;

            if (targetIndex != -1) {
                mediaExtractor.selectTrack(targetIndex);
            }

            MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
            info.presentationTimeUs = 0;
            // ByteBuffer 不能太小
            ByteBuffer byteBuf = ByteBuffer.allocate(500 * 1024);
            int sample_size;
            while ((sample_size = mediaExtractor.readSampleData(byteBuf, 0)) > 0) {
                info.offset = 0;
                info.size = sample_size;
                info.flags = MediaCodec.BUFFER_FLAG_KEY_FRAME;
                info.presentationTimeUs += 1000 * 1000 / frame_rate;
                mediaMuxer.writeSampleData(video_track, byteBuf, info);
                mediaExtractor.advance();
            }

            mediaExtractor.release();
            mediaMuxer.stop();
            mediaMuxer.release();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
