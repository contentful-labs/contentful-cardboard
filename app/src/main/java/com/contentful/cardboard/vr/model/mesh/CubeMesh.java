package com.contentful.cardboard.vr.model.mesh;

import com.contentful.cardboard.vr.util.BufferUtils;

public class CubeMesh extends Mesh {

  public static final float[] COORDS = new float[]{
      // Front face
      -1.0f, 1.0f, 1.0f,
      -1.0f, -1.0f, 1.0f,
      1.0f, 1.0f, 1.0f,
      -1.0f, -1.0f, 1.0f,
      1.0f, -1.0f, 1.0f,
      1.0f, 1.0f, 1.0f,

      // Right face
      1.0f, 1.0f, 1.0f,
      1.0f, -1.0f, 1.0f,
      1.0f, 1.0f, -1.0f,
      1.0f, -1.0f, 1.0f,
      1.0f, -1.0f, -1.0f,
      1.0f, 1.0f, -1.0f,

      // Back face
      1.0f, 1.0f, -1.0f,
      1.0f, -1.0f, -1.0f,
      -1.0f, 1.0f, -1.0f,
      1.0f, -1.0f, -1.0f,
      -1.0f, -1.0f, -1.0f,
      -1.0f, 1.0f, -1.0f,

      // Left face
      -1.0f, 1.0f, -1.0f,
      -1.0f, -1.0f, -1.0f,
      -1.0f, 1.0f, 1.0f,
      -1.0f, -1.0f, -1.0f,
      -1.0f, -1.0f, 1.0f,
      -1.0f, 1.0f, 1.0f,

      // Top face
      -1.0f, 1.0f, -1.0f,
      -1.0f, 1.0f, 1.0f,
      1.0f, 1.0f, -1.0f,
      -1.0f, 1.0f, 1.0f,
      1.0f, 1.0f, 1.0f,
      1.0f, 1.0f, -1.0f,

      // Bottom face
      1.0f, -1.0f, -1.0f,
      1.0f, -1.0f, 1.0f,
      -1.0f, -1.0f, -1.0f,
      1.0f, -1.0f, 1.0f,
      -1.0f, -1.0f, 1.0f,
      -1.0f, -1.0f, -1.0f,
  };

  public static final float[] NORMALS = new float[]{
      // Front face
      0.0f, 0.0f, 1.0f,
      0.0f, 0.0f, 1.0f,
      0.0f, 0.0f, 1.0f,
      0.0f, 0.0f, 1.0f,
      0.0f, 0.0f, 1.0f,
      0.0f, 0.0f, 1.0f,

      // Right face
      1.0f, 0.0f, 0.0f,
      1.0f, 0.0f, 0.0f,
      1.0f, 0.0f, 0.0f,
      1.0f, 0.0f, 0.0f,
      1.0f, 0.0f, 0.0f,
      1.0f, 0.0f, 0.0f,

      // Back face
      0.0f, 0.0f, -1.0f,
      0.0f, 0.0f, -1.0f,
      0.0f, 0.0f, -1.0f,
      0.0f, 0.0f, -1.0f,
      0.0f, 0.0f, -1.0f,
      0.0f, 0.0f, -1.0f,

      // Left face
      -1.0f, 0.0f, 0.0f,
      -1.0f, 0.0f, 0.0f,
      -1.0f, 0.0f, 0.0f,
      -1.0f, 0.0f, 0.0f,
      -1.0f, 0.0f, 0.0f,
      -1.0f, 0.0f, 0.0f,

      // Top face
      0.0f, 1.0f, 0.0f,
      0.0f, 1.0f, 0.0f,
      0.0f, 1.0f, 0.0f,
      0.0f, 1.0f, 0.0f,
      0.0f, 1.0f, 0.0f,
      0.0f, 1.0f, 0.0f,

      // Bottom face
      0.0f, -1.0f, 0.0f,
      0.0f, -1.0f, 0.0f,
      0.0f, -1.0f, 0.0f,
      0.0f, -1.0f, 0.0f,
      0.0f, -1.0f, 0.0f,
      0.0f, -1.0f, 0.0f
  };

  public static final float[] UVS = new float[]{
      // Front face
      0.0f, 1.0f,
      0.0f, 0.0f,
      1.0f, 1.0f,
      //
      0.0f, 0.0f,
      1.0f, 0.0f,
      1.0f, 1.0f,

      // Right face
      1.0f, 1.0f,
      1.0f, 0.0f,
      1.0f, 1.0f,
      //
      1.0f, 0.0f,
      1.0f, 0.0f,
      1.0f, 1.0f,

      // Back face
      1.0f, 1.0f,
      1.0f, 0.0f,
      0.0f, 1.0f,
      //
      1.0f, 0.0f,
      0.0f, 0.0f,
      0.0f, 1.0f,

      // Left face
      0.0f, 1.0f,
      0.0f, 0.0f,
      0.0f, 1.0f,
      //
      0.0f, 0.0f,
      0.0f, 0.0f,
      0.0f, 1.0f,

      // Top face
      0.0f, 1.0f,
      0.0f, 1.0f,
      1.0f, 1.0f,
      //
      0.0f, 1.0f,
      1.0f, 1.0f,
      1.0f, 1.0f,

      // Bottom face
      1.0f, 0.0f,
      1.0f, 0.0f,
      0.0f, 0.0f,
      //
      1.0f, 0.0f,
      0.0f, 0.0f,
      0.0f, 0.0f,
  };

  public CubeMesh() {
    super(
        BufferUtils.floatArrayToNativeFloatBuffer(COORDS),
        BufferUtils.floatArrayToNativeFloatBuffer(NORMALS),
        BufferUtils.floatArrayToNativeFloatBuffer(UVS)
    );
  }

}
