Camera Preview Callback的YUV常用格式有两种：
一个是NV21，一个是YV12,Android一般默认使用YCbCr_420_SP的格式（NV21）。

在Android框架里面两个基本的类OpenGL ES API创建和操作图形： GLSurfaceView 和 GLSurfaceView.Renderer。

GLSurfaceView
这是一个视图类，可以使用OpenGL API来绘制和操作图形对象，
这一点在功能上很类似于SurfaceView。可以通过创建一个SurfaceView的实例并添加你的渲染器来使用这个类。
但是如果想要捕捉触摸屏的事件，则应该扩展GLSurfaceView以实现触摸监听器。

GLSurfaceView.Renderer
此接口定义了在GLSurfaceView中绘制图形所需的方法。
必须将此接口的实现作为单独的类提供，并使用GLSurfaceView.setRenderer()将其附加到您的GLSurfaceView实例。
GLSurfaceView.Renderer要求实现以下方法：

onSurfaceCreated()：创建GLSurfaceView时，系统调用一次该方法。使用此方法执行只需要执行一次的操作，
例如设置OpenGL环境参数或初始化OpenGL图形对象。
onDrawFrame()：系统在每次重画GLSurfaceView时调用这个方法。使用此方法作为绘制（和重新绘制）图形对象的主要执行方法。
onSurfaceChanged()：当GLSurfaceView的发生变化时，系统调用此方法，这些变化包括GLSurfaceView的大小或设备屏幕方向的变化。
例如：设备从纵向变为横向时，系统调用此方法。我们应该使用此方法来响应GLSurfaceView容器的改变。

为了程序能够使用OpenGL ES 2.0的API，你必须添加以下声明到manifest：
<uses-feature android:glEsVersion="0x00020000" android:required="true" />
如果你的应用程序需要使用纹理压缩，你还需要声明你的应用程序需要支持哪种压缩格式。
<supports-gl-texture android:name="GL_OES_compressed_ETC1_RGB8_texture" />
<supports-gl-texture android:name="GL_OES_compressed_paletted_texture" />


android平台上EGL为openGL提供上下文环境以及窗口管理。

状态机是一个抽象的模型，表示一组状态变量的集合。

OpenGL中的缓冲区：颜色缓冲区、深度缓冲区、模版缓冲区。
Framebuffer指的是所有这些缓冲区一起串联工作。

OpenGL中图元都是一维、二维、三维的物体

三种方式向着色器传递渲染数据：
属性、uniform值、纹理

属性之总是以四维向量的形式进行内部存储。

在默认情况下，OpenGL认为具有逆时针方向环绕的多边形是正面的，顺时针方向环绕的多边形是背面的。
调用glFrontFace()可以改变这种默认行为。

OpenGL3.0引入了GLSL1.3版本
OpenGL3.1引入了GLSL1.4版本
OpenGL3.2引入了GLSL1.5版本















