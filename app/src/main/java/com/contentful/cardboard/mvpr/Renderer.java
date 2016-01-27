package com.contentful.cardboard.mvpr;

import com.contentful.cardboard.vr.model.Model;
import com.contentful.cardboard.vr.model.ProtoModel;

import java.util.List;

public interface Renderer {
  void surfaceCreated();

  void addProtoModel(ProtoModel protoModel);

  void prepare();

  void render(float[] perspective, float[] eyeView);

  List<Model> getForegroundModels();

  List<Model> getBackgroundModels();
}
