package com.contentful.cardboard.vr.model.mesh;

import com.contentful.cardboard.vr.util.BufferUtils;

public class QuadMesh extends Mesh {

  public static final float[] COORDS = new float[]{
      -1.0f, -1.0f, 0.0f,
      1.0f, -1.0f, 0.0f,
      1.0f, 1.0f, 0.0f,

      1.0f, 1.0f, 0.0f,
      -1.0f, 1.0f, 0.0f,
      -1.0f, -1.0f, 0.0f,
  };

  public static final float[] NORMALS = new float[]{
      0.0f, 0.0f, -1.0f,
      0.0f, 0.0f, -1.0f,
      0.0f, 0.0f, -1.0f,

      0.0f, 0.0f, -1.0f,
      0.0f, 0.0f, -1.0f,
      0.0f, 0.0f, -1.0f,
  };

  public static final float[] UVS = new float[]{
      0.0f, 1.0f,
      1.0f, 1.0f,
      1.0f, 0.0f,

      1.0f, 0.0f,
      0.0f, 0.0f,
      0.0f, 1.0f,
  };

  public QuadMesh() {
    super(
        BufferUtils.floatArrayToNativeFloatBuffer(COORDS),
        BufferUtils.floatArrayToNativeFloatBuffer(NORMALS),
        BufferUtils.floatArrayToNativeFloatBuffer(UVS)
    );
  }
}
