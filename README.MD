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

OpenGL只能画点、直线、三角形

针对每一个顶点，顶点着色器都会被调用一次

OpenGL程序就是把顶点着色器和片段着色器链接在一起
顶点着色器和片段着色器总是配合工作


绕 X 轴旋转矩阵
1       0       0       0
0     cos(a)  -sin(a)   0
0     sin(a)  cos(a)    0
0       0       0       0


绕 Y 轴旋转矩阵
cos(a)    0     sin(a)    0
  0       1       0       0
-sin(a)   0     cos(a)    0
  0       0       0       0


绕 X 轴旋转矩阵
cos(a)     -sin(a)  0   0
sin(a)     cos(a)   0   0
  0           0     1   0
  0           0     0   1


OpenGL 中的纹理可以用来表示图像、照片、甚至由一个数学算法生成的分形数据。

纹理过滤
当纹理大小被扩大或者缩小的时候 需要使用纹理过滤做相应的处理。
最近邻过滤：
    放大纹理时，它的锯齿效果看起来很明显。

双线性过滤：
    双线性过滤使用双线性插值平滑像素之间的过度。比较适合处理放大，处理缩小时，缩小到一定的大小就效果不好。


## 获取着色器程序内成员变量的id
GLES20.glGetAttribLocation 获取着色器程序中，指定为attribute类型变量的id。
GLES20.glGetUniformLocation 获取着色器程序中，指定为uniform类型变量的id。

## 向着色器传递数据
void glVertexAttribPointer (int index, int size, int type, boolean normalized, int stride, Buffer ptr)
参数含义：
index：指定要修改的顶点着色器中顶点变量id；
size：指定每个顶点属性的组件数量。必须为1、2、3或者4。如position是由3个（x,y,z）组成，而颜色是4个（r,g,b,a））；
type：指定数组中每个组件的数据类型。可用的符号常量有GL_BYTE, GL_UNSIGNED_BYTE, GL_SHORT,GL_UNSIGNED_SHORT, GL_FIXED, 和 GL_FLOAT，初始值为GL_FLOAT；
normalized：指定当被访问时，固定点数据值是否应该被归一化（GL_TRUE）或者直接转换为固定点值（GL_FALSE）；
stride：指定连续顶点属性之间的偏移量。如果为0，那么顶点属性会被理解为：它们是紧密排列在一起的。初始值为0。
如果normalized被设置为GL_TRUE，意味着整数型的值会被映射至区间-1,1，或者区间[0,1]（无符号整数），
反之，这些值会被直接转换为浮点值而不进行归一化处理；
ptr：顶点的缓冲数据。

开源三维库：libgdx

OpenGL中基本的渲染单元成为图元
三种基本的可渲染的图元：点、线、三角形

光栅器是专门用来将三维形式的三角形转换为在屏幕上进行渲染的像素的硬件。


创建shader：
1、创建shader对象
2、绑定source到shader对象
3、编译shader

创建program：
1、创建progrm对象
2、绑定shader到program对象
3、链接program
4、删除shader对象[优化，一旦一个着色器对象被链接到program中之后，这个program对象就包含了相应的二进制代码]




































