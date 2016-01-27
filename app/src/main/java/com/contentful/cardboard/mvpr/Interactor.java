package com.contentful.cardboard.mvpr;

import com.contentful.cardboard.vr.model.ProtoModel;

public interface Interactor {
  void setListener(Listener listener);

  void requestModels();

  interface Listener {
    void onModelReceived(ProtoModel model);

    void onError(Throwable throwable);
  }
}
