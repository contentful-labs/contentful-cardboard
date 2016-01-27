package com.contentful.cardboard.vr.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class BufferUtils {
  public static FloatBuffer floatArrayToNativeFloatBuffer(float[] floats) {
    FloatBuffer floatBuffer = ByteBuffer.allocateDirect(floats.length * 4)
        .order(ByteOrder.nativeOrder())
        .asFloatBuffer();
    floatBuffer.put(floats)
        .position(0);
    return floatBuffer;
  }
}
