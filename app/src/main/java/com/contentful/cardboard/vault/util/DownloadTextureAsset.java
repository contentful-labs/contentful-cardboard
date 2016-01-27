package com.contentful.cardboard.vault.util;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.contentful.cardboard.vr.model.ProtoModel;

import rx.Observable;
import rx.functions.Func1;

public class DownloadTextureAsset implements Func1<ProtoModel, Observable<ProtoModel>> {

  private final Context context;

  public DownloadTextureAsset(Context context) {
    this.context = context;
  }

  @Override public Observable<ProtoModel> call(ProtoModel protoModel) {
    byte[] bytes = Downloader.download(context, protoModel.textureUrl).toByteArray();

    protoModel.texture = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

    return Observable.just(protoModel);
  }
}
