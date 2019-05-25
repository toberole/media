package com.xxx.media.uttils;

public class VoiceUtil {
    public static float decibel(byte[] voice) {
        float ret = 0;
        if (voice != null && voice.length > 0) {
            long v = 0;
            for (int i = 0; i < voice.length; i++) {
                v += voice[i] * voice[i];
            }

            float mean = v / voice.length;
            if (mean != 0) {
                ret = (float) (10 * Math.log10(mean));
            }
        }

        return ret % 100;
    }
}
