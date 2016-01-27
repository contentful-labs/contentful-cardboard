package com.contentful.cardboard.vault;

import com.contentful.vault.Asset;
import com.contentful.vault.ContentType;
import com.contentful.vault.Field;
import com.contentful.vault.Resource;

import java.util.List;

@ContentType("product")
public class Product extends Resource {
  @Field
  String name;

  @Field
  String description;

  @Field
  Double rating;

  @Field
  Double price;

  @Field
  Asset meshFile;

  @Field
  Asset previewMeshFile;

  @Field
  List<Asset> textures;

  public String name() {
    return name;
  }

  public String description() {
    return description;
  }

  public Double rating() {
    return rating;
  }

  public Double price() {
    return price;
  }

  public Asset meshFile() {
    return meshFile;
  }

  public Asset previewMeshFile() {
    return previewMeshFile;
  }

  public List<Asset> textures() {
    return textures;
  }
}
