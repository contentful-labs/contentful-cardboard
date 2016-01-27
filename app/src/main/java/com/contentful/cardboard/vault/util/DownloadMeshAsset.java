package com.contentful.cardboard.vault.util;

import android.content.Context;

import com.contentful.cardboard.vr.model.ProtoModel;
import com.contentful.cardboard.vr.util.ObjParser;

import rx.Observable;
import rx.functions.Func1;

public class DownloadMeshAsset implements Func1<ProtoModel, Observable<ProtoModel>> {

  private final Context context;

  public DownloadMeshAsset(Context context) {
    this.context = context;
  }

  @Override public Observable<ProtoModel> call(ProtoModel protoModel) {

    final String source = Downloader.download(context, protoModel.meshUrl).toString();
    protoModel.mesh = new ObjParser().loadMeshFromString(source);

    return Observable.just(protoModel);
  }
}
