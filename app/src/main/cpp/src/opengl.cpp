// 使用EGL
#include <EGL/egl.h>
#include <EGL/eglext.h>

// 使用OpenGL ES
#include <GLES2/gl2.h>
#include <GLES2/gl2ext.h>

#include <android/native_window.h>
#include <android/native_window_jni.h>

#include <jni.h>
#include "log.h"

#include "com_xxx_media_dbl_Media.h"

/**
 * 使用EGL搭建OpenGL的上下文环境以及渲染到目标屏幕
 * 首先EGL需要知道绘制目标在上面地方，EGLDisplay是一个封装了系统物理屏幕的数据类型
 * 通常会调用eglGetDisplay获取OpenGL的渲染目标，
*/

/*
 * Class:     com_xxx_media_dbl_Media
 * Method:    createEGL
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_xxx_media_dbl_Media_createEGL
        (JNIEnv *env, jclass jclazz, jobject jsurface) {
    // 获取平台默认的显示设备
    EGLDisplay display = eglGetDisplay(EGL_DEFAULT_DISPLAY);
    if (display == EGL_NO_DISPLAY) {
        LOGI("没有显示设备 error = %d", eglGetError());
        return;
    }

    // 初始化显示设备
    EGLBoolean init_res = eglInitialize(display, NULL, NULL);
    if (!init_res) {
        LOGI("初始化显示设备 error = %d", eglGetError());
        return;
    }

    // 一旦EGL有了display之后，它就可以将OpenGL ES的输出和设备的屏幕连接起来
    // 需要指定一些配置，比如色彩格式、像素格式、RGBA的表示以及SurfaceType，不同平台的EGL标出配置不同
    // android EGL配置
    // eglChooseConfig (EGLDisplay dpy, const EGLint *attrib_list, EGLConfig *configs, EGLint config_size, EGLint *num_config);
    const EGLint attrib_list[] = {
            EGL_BUFFER_SIZE, 32,
            EGL_ALPHA_SIZE, 8,
            EGL_BLUE_SIZE, 8,
            EGL_GREEN_SIZE, 8,
            EGL_RED_SIZE, 8,
            EGL_RENDERABLE_TYPE, EGL_OPENGL_ES2_BIT,
            EGL_SURFACE_TYPE, EGL_WINDOW_BIT,
            EGL_NONE
    };

    EGLConfig config;
    EGLint config_size = 1;
    EGLint num_config;
    EGLBoolean config_res = eglChooseConfig(display, attrib_list, &config, config_size,
                                            &num_config);
    if (!config_res) {
        LOGI("配置失败 error = %d", eglGetError());
        return;
    }

    // 创建EGL好上下文环境 EGLContext
    // opengl 指令必须在其OpenGL上下文环境中运行
    // eglCreateContext (EGLDisplay dpy, EGLConfig config, EGLContext share_context, const EGLint *attrib_list);
    /**
     * share_context 改变量是指可以与正在创建的上下文环境共享OpenGL资源，包括纹理ID
     * FrameBuffer以及其他的buffer资源
     */
    EGLint attributes[] = {EGL_CONTEXT_CLIENT_VERSION, 2, EGL_NONE};
    EGLContext egl_ctx = eglCreateContext(display, config, NULL, attributes);
    if (!egl_ctx) {
        LOGI("创建OpenGL上下文环境失败 error = %d", eglGetError());
        return;
    }

    LOGI("创建OpenGL上下文环境成功");

    // 连接EGL和设备屏幕
    /**
     * 使用EGLSurface
     * Surface实际上是一个FrameBuffer
     * 通过EGL库提供的eglCreateWindowSurface可以创建一个可实际显示的Surface。
     * 使用eglCreatePbufferSurface可以创建一个OffScreen的Surface
     */

    EGLSurface egl_surface = NULL;
    EGLint format;
    if (!eglGetConfigAttrib(display, config, EGL_NATIVE_VISUAL_ID, &format)) {
        LOGI("eglGetConfigAttrib error = %d", eglGetError());
        return;
    }
    /**
     * _window是通过java层的Surface对象创建的ANativeWindow类型的对象，即本地设备的屏幕表示
     * 在Android中可以通过Surface【SurfaceView或者是TextureView来得到或者构建出的Surface】来构建ANativeWindow
     */
    ANativeWindow *_window = ANativeWindow_fromSurface(env, jsurface);
    ANativeWindow_setBuffersGeometry(_window, 0, 0, format);
    if (!(egl_surface = eglCreateWindowSurface(display, config, _window, 0))) {
        LOGI("eglCreateWindowSurface error = %d", eglGetError());
        return;
    }

    /**
     * 如果是需要做离线的渲染，即在后台使用OpenGl进行一些图像的处理
     * 需要使用离线的Surface
     */
    // 创建离线的Surface
//    EGLSurface p_surface = NULL;
//    EGLint width = 0;
//    EGLint height = 0;
//    EGLint pbufferAttributes[] = {EGL_WIDTH, width, EGL_HEIGHT, height, EGL_NONE, EGL_NONE};
//    if (!(p_surface = eglCreatePbufferSurface(display, config, pbufferAttributes))) {
//        LOGI("eglCreatePbufferSurface error = %d", eglGetError());
//        return;
//    }

    /**
     * 开发者需要开辟新的线程
     * 来执行OpenGL ES的渲染操作，而且必须为改线程绑定显示设备【Surface】和上下文环境【Context】
     * 因为每个线程都需要绑定一个上下文，这样才可以执行OpenGL的指令
     * 调用eglMakeCurrent来为改线程绑定Surface与Context
     */

    // 绑定Surface与Context
    // eglMakeCurrent(display, egl_surface, egl_surface, egl_ctx);

    // 销毁资源也必须在绑定的线程中执行
    // 销毁显示设备
    // eglDestroySurface(display, egl_surface);
    // 销毁上下文
    // eglDestroyContext(display, egl_ctx);

    // 创建纹理对象
    // glGenTextures (GLsizei n, GLuint *textures);
    /**
     * glGenTextures (GLsizei n, GLuint *textures);
     * n: 创建纹理对象的个数
     * textures：用于接收创建的纹理对象
     */
    GLsizei n = 1;
    GLuint texId = NULL;
    glGenTextures(n, &texId);

    // 绑定纹理对象 否则opengl不知道操作哪一个纹理
    glBindTexture(GL_TEXTURE_2D, texId);
    // 解除纹理绑定
    glBindTexture(GL_TEXTURE_2D, 0);

    /**
     * 当纹理对象被渲染到物体表面时，纹理有可能被放大或者时缩小
     * 需要配置每一个像素是如何被填充的，可以通过纹理过滤器来指定
     *
     * GL_LINEAR即线性过滤方式
     */
    //  放大的填充模式
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    //  缩小的填充模式
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

    // 纹理坐标映射
    /**
     *  glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_S,GL_CLAMP_TO_EDGE);
     *  glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_T,GL_CLAMP_TO_EDGE);
     *  将改纹理的s轴和t轴的坐标设置为GL_CLAMP_TO_EDGE类型，因为纹理坐标可以超出（0，1）的范围
     *  而按照上述设置规则 大于1的都设置为1，小于0的都设置为0
     */
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

    // 获取图片数据
    // glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);

    // 指定引擎偏重于视觉质量 or 速度 依赖具体的硬件
    // glHint (GLenum target, GLenum mode);

    /**
     * 操作状态变量
     * glEnable (GLenum cap);
     * glDisable()
     * glIsEnabled()
     * // 开启表面剔除
     * glEnable(GL_CULL_FACE)
     * glCullFace()
     */

//    glCopyTexImage2D();
//    glTexParameteri();

    /**
     * 着色器
     * 1、编译 glCompileShader()
     * 2、连接绑定
     * glCreateProgram()
     * glAttachShader()
     * glLinkProgram()
     */




}

