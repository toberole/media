package com.xxx.media.gl.util;

import android.opengl.GLES20;

import java.util.ArrayList;
import java.util.List;

public class ObjectBuilder {
    public static final int FLOATS_PRE_VERTEX = 3;

    private List<DrawCommand> drawList = new ArrayList<>();

    private float[] vertexData;
    private int offset = 0;

    private int startVertex = offset / FLOATS_PRE_VERTEX;
    private static int numVertices = 0;

    private ObjectBuilder(int sizeinVertices) {
        vertexData = new float[sizeinVertices * FLOATS_PRE_VERTEX];
    }

    public static GeneratedData createPuck(Geometry.Cylinder puck, int numPoints) {
        numVertices = sizeOfCircleInVertices(numPoints);

        int size = sizeOfOpenCylinderInVertices(numPoints) + sizeOfCircleInVertices(numPoints);
        ObjectBuilder builder = new ObjectBuilder(size);

        Geometry.Circle puckTop = new Geometry.Circle(puck.center.translayeY(puck.height / 2f),
                puck.radius);

        builder.appendCircle(puckTop, numPoints);
        builder.appendOpenCylinder(puck, numPoints);

        return builder.build();
    }

    public static GeneratedData createMallet(Geometry.Point center, float radius,
                                             float height, int numPoints) {
        numVertices = sizeOfCircleInVertices(numPoints);

        int size = sizeOfCircleInVertices(numPoints) * 2 + sizeOfOpenCylinderInVertices(numPoints) * 2;
        ObjectBuilder builder = new ObjectBuilder(size);
        float baseheight = height * 0.25f;
        Geometry.Circle baseCircle = new Geometry.Circle(center.translayeY(-baseheight), radius);
        Geometry.Cylinder baseCylinder = new Geometry.Cylinder(baseCircle.center.translayeY(-baseheight / 2f), radius, baseheight);
        builder.appendCircle(baseCircle, numPoints);
        builder.appendOpenCylinder(baseCylinder, numPoints);
        return builder.build();
    }

    private GeneratedData build() {
        return new GeneratedData(vertexData, drawList);
    }

    private void appendOpenCylinder(Geometry.Cylinder cylinder, final int numPoints) {
        final int startVertex = offset / FLOATS_PRE_VERTEX;
        final int numVertices = sizeOfOpenCylinderInVertices(numPoints);
        float yStart = cylinder.center.y - cylinder.height / 2f;
        float yEnd = cylinder.center.y + cylinder.height / 2f;

        for (int i = 0; i <= numPoints; i++) {
            float angleInRadians = (float) (1.0f * i / numPoints * Math.PI * 2);
            float xPosition = (float) (cylinder.center.x + cylinder.radius * Math.cos(angleInRadians));
            float zPosition = (float) (cylinder.center.z + cylinder.radius * Math.sin(angleInRadians));

            vertexData[offset++] = xPosition;
            vertexData[offset++] = yStart;
            vertexData[offset++] = zPosition;

            vertexData[offset++] = xPosition;
            vertexData[offset++] = yEnd;
            vertexData[offset++] = zPosition;
        }

        drawList.add(new DrawCommand() {
            @Override
            public void draw() {
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, startVertex, numVertices);
            }
        });
    }

    private void appendCircle(Geometry.Circle circle, int numPoints) {
        vertexData[offset++] = circle.center.x;
        vertexData[offset++] = circle.center.y;
        vertexData[offset++] = circle.center.z;

        for (int i = 0; i <= numPoints; i++) {
            float angleInRadians = (float) (1.0f * i / numPoints * Math.PI * 2f);

            vertexData[offset++] = (float) (circle.center.x + circle.radius * Math.cos(angleInRadians));
            vertexData[offset++] = circle.center.y;
            vertexData[offset++] = (float) (circle.center.z + circle.radius * Math.sin(angleInRadians));
        }

        drawList.add(new DrawCommand() {
            @Override
            public void draw() {
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, startVertex, numVertices);
            }
        });
    }

    private static int sizeOfCircleInVertices(int numPoints) {
        return 1 + (numPoints + 1);
    }

    private static int sizeOfOpenCylinderInVertices(int numPoints) {
        return (numPoints + 1) * 2;
    }

    public static class GeneratedData {
        float[] vertexData;
        List<DrawCommand> drawList;

        public GeneratedData(float[] vertexData, List<DrawCommand> drawList) {
            this.vertexData = vertexData;
            this.drawList = drawList;
        }
    }

    public interface DrawCommand {
        void draw();
    }
}
