package com.contentful.cardboard.mvpr;

import com.contentful.cardboard.vr.model.ProtoModel;

public class Presenter implements Interactor.Listener {
  private final Interactor interactor;
  private View view;

  private int modelsReceived = 0;

  public Presenter(Interactor interactor) {
    this.interactor = interactor;
    this.interactor.setListener(this);
  }

  public void bind(View view) {
    this.view = view;
    interactor.requestModels();
  }

  public void unbind() {
    this.view = null;
  }

  @Override public void onModelReceived(ProtoModel model) {
    model.position[0] += modelsReceived * 4.0f;
    view.addProtoModel(model);
    modelsReceived++;
  }

  @Override public void onError(Throwable throwable) {
    if (view != null) {
      view.showError(throwable);
    }
  }
}
