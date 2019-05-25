#include "com_xxx_media_dbl_Media.h"
#include <jni.h>
#include <string>

#include <SLES/OpenSLES.h>
#include <SLES/OpenSLES_Android.h>

/*
 * Class:     com_xxx_media_dbl_Media
 * Method:    playPCM
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_xxx_media_dbl_Media_playPCM
        (JNIEnv *env, jclass jclazz, jstring jurl) {
    const char *url = env->GetStringUTFChars(jurl, NULL);



}

/*
 * Class:     com_xxx_media_dbl_Media
 * Method:    startRecord
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_xxx_media_dbl_Media_startRecord
        (JNIEnv *env, jclass jclazz) {
    return 0;
}

/*
 * Class:     com_xxx_media_dbl_Media
 * Method:    stopRecord
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_xxx_media_dbl_Media_stopRecord
        (JNIEnv *env, jclass jclazz) {
    return 0;
}
