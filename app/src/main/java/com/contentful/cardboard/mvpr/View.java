package com.contentful.cardboard.mvpr;

import com.contentful.cardboard.vr.model.ProtoModel;

public interface View {
  void addProtoModel(ProtoModel protomodel);

  void showError(Throwable throwable);
}
