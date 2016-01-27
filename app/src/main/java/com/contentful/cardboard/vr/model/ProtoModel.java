package com.contentful.cardboard.vr.model;

import android.graphics.Bitmap;

import com.contentful.cardboard.vr.model.mesh.Mesh;

public class ProtoModel {
  public String name;

  public Bitmap signTexture;

  public String textureUrl;
  public Bitmap texture;

  public Mesh mesh;
  public String meshUrl;

  public float[] position;
  public float[] scale;
  public float[] rotation;
}
