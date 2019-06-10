package com.xxx.media;

public class Vertices {
    public static float[] tableVerticesWithTriangles = new float[]{
            // x y z w r g b
            0.0f, 0.0f, 0f, 1.0f, 1f, 1f, 1f,
            -0.5f, -0.8f, 0f, 1f, 0.7f, 0.7f, 0.7f,
            0.5f, -0.8f, 0f, 1f, 0.7f, 0.7f, 0.7f,

            0.5f, 0.8f, 0f, 1f, 0.7f, 0.7f, 0.7f,
            -0.5f, 0.8f, 0f, 1f, 0.7f, 0.7f, 0.7f,
            -0.5f, -0.8f, 0f, 1f, 0.7f, 0.7f, 0.7f,

            // line1
            -0.5f, 0.0f, 0f, 1f, 1f, 0f, 0f,
            0.5f, 0.0f, 0f, 1f, 1f, 0f, 0f,

            // mallets
            0.0f, -0.4f, 0f, 1f, 0f, 0f, 1f,
            0.0f, 0.4f, 0f, 1f, 1f, 0f, 0f
    };
    public static float[] tableVerticesWithTriangles4 = new float[]{
            // x y z w r g b
            0.0f, 0.0f, 0f, 1.5f, 1f, 1f, 1f,
            -0.5f, -0.8f, 0f, 1f, 0.7f, 0.7f, 0.7f,
            0.5f, -0.8f, 0f, 1f, 0.7f, 0.7f, 0.7f,

            0.5f, 0.8f, 0f, 2f, 0.7f, 0.7f, 0.7f,
            -0.5f, 0.8f, 0f, 2f, 0.7f, 0.7f, 0.7f,
            -0.5f, -0.8f, 0f, 1f, 0.7f, 0.7f, 0.7f,

            // line1
            -0.5f, 0.0f, 0f, 1.5f, 1f, 0f, 0f,
            0.5f, 0.0f, 0f, 1.5f, 1f, 0f, 0f,

            // mallets
            0.0f, -0.4f, 0f, 1.25f, 0f, 0f, 1f,
            0.0f, 0.4f, 0f, 1.75f, 1f, 0f, 0f
    };
    public static float[] tableVerticesWithTriangles3 = new float[]{
            // x y r g b
            0.0f, 0.0f, 1f, 1f, 1f,
            -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
            0.5f, -0.8f, 0.7f, 0.7f, 0.7f,

            0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
            -0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
            -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,

            // line1
            -0.5f, 0.0f, 1f, 0f, 0f,
            0.5f, 0.0f, 1f, 0f, 0f,

            // mallets
            0.0f, -0.4f, 0f, 0f, 1f,
            0.0f, 0.4f, 1f, 0f, 0f
    };

    public static float[] tableVerticesWithTriangles2 = new float[]{
            // Triangle1
            -0.5f, -0.5f,
            0.5f, 0.5f,
            -0.5f, 0.5f,

            // Triangle2
            -0.5f, -0.5f,
            0.5f, -0.5f,
            0.5f, 0.5f,

            // line1
            -0.5f, 0.0f,
            0.5f, 0.0f,

            // mallets
            0.0f, -0.25f,
            0.0f, 0.25f
    };


    public static float[] tableVerticesWithTriangles1 = new float[]{
            // Triangle1
            0.0f, 0.0f,
            9.0f, 14.0f,
            0.0f, 14.0f,

            // Triangle2
            0.0f, 0.0f,
            9.0f, 0.0f,
            9.0f, 14.0f,

            // line1
            0.0f, 7.0f,
            9.0f, 7.0f,

            // mallets
            4.5f, 2.0f,
            4.5f, 12.0f
    };

    public static float[] tableVertices = new float[]{
            0.0f, 0.0f,
            0.0f, 14.0f,
            9.0f, 14.0f,
            9.0f, 0.0f
    };

    public static float[] vertices = new float[]{
            0.0f, 7.0f,
            9.0f, 7.0f,

            4.5f, 2.0f,
            4.5f, 12.0f
    };
}
