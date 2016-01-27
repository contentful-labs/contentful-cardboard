package com.contentful.cardboard.vr.model.mesh;

import java.nio.FloatBuffer;

public class Mesh {
  private final FloatBuffer vertices;
  private final FloatBuffer normals;
  private final FloatBuffer uvs;

  public Mesh(FloatBuffer vertices, FloatBuffer normals, FloatBuffer uvs) {
    this.vertices = vertices;
    this.normals = normals;
    this.uvs = uvs;
  }

  public FloatBuffer getVertices() {
    return vertices;
  }

  public FloatBuffer getNormals() {
    return normals;
  }

  public FloatBuffer getUvs() {
    return uvs;
  }
}
