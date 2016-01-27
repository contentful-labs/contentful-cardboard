package com.contentful.cardboard.vr.model;

import android.opengl.Matrix;

import com.contentful.cardboard.vr.model.mesh.Mesh;
import com.contentful.cardboard.vr.model.shader.Material;

public class Model {

  private final String name;

  private final Mesh mesh;

  private final float[] position = new float[4];
  private final float[] rotation = new float[4];
  private final float[] scale = new float[4];

  private final Material material;
  private boolean dirty;

  private float[] lastModelMatrix;

  public Model(String name, Mesh mesh, Material material) {
    this.name = name;
    this.material = material;
    this.mesh = mesh;
    this.lastModelMatrix = new float[16];
    this.scale[0] = this.scale[1] = this.scale[2] = this.scale[3] = 1.0f;
    this.position[3] = 1.0f;
    this.rotation[3] = 1.0f;
    dirty = true;
  }

  public String getName() {
    return name;
  }

  public Material getMaterial() {
    return material;
  }

  public float[] getPosition() {
    return position;
  }

  public float[] getRotation() {
    return rotation;
  }

  public float[] getScale() {
    return scale;
  }

  public Model rotateBy(float x, float y, float z) {
    rotation[0] += x;
    rotation[1] += y;
    rotation[2] += z;
    dirty = true;
    return this;
  }

  public Model rotateBy(float[] vector) {
    rotateBy(vector[0], vector[1], vector[2]);
    return this;
  }


  public Model moveBy(float x, float y, float z) {
    position[0] += x;
    position[1] += y;
    position[2] += z;
    dirty = true;
    return this;
  }

  public Model moveTo(float x, float y, float z) {
    position[0] = x;
    position[1] = y;
    position[2] = z;
    dirty = true;

    return this;
  }

  public Model moveBy(float[] vector) {
    moveBy(vector[0], vector[1], vector[2]);
    return this;
  }

  public Model scaleBy(float x, float y, float z) {
    scale[0] *= x;
    scale[1] *= y;
    scale[2] *= z;
    dirty = true;
    return this;
  }

  public Model scaleBy(float[] vector) {
    scaleBy(vector[0], vector[1], vector[2]);
    return this;
  }

  public Mesh getMesh() {
    return mesh;
  }

  public float[] createModelMatrix() {
    if (dirty || lastModelMatrix == null) {
      Matrix.setIdentityM(lastModelMatrix, 0);
      Matrix.translateM(lastModelMatrix, 0, position[0], position[1], position[2]);
      Matrix.rotateM(lastModelMatrix, 0, rotation[0], 1, 0, 0);
      Matrix.rotateM(lastModelMatrix, 0, rotation[1], 0, 1, 0);
      Matrix.rotateM(lastModelMatrix, 0, rotation[2], 0, 0, 1);
      Matrix.scaleM(lastModelMatrix, 0, scale[0], scale[1], scale[2]);
    }

    dirty = false;
    return lastModelMatrix;
  }
}
