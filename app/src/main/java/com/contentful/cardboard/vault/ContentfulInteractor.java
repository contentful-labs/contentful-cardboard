package com.contentful.cardboard.vault;

import android.content.Context;
import android.util.Log;

import com.contentful.cardboard.mvpr.Interactor;
import com.contentful.cardboard.vault.util.CreateProtoModelAndRenderSign;
import com.contentful.cardboard.vault.util.DownloadMeshAsset;
import com.contentful.cardboard.vault.util.DownloadTextureAsset;
import com.contentful.cardboard.vr.model.ProtoModel;
import com.contentful.java.cda.CDAClient;
import com.contentful.vault.SyncResult;
import com.contentful.vault.Vault;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ContentfulInteractor implements Interactor {

  private final Vault vault;
  private final Context applicationContext;

  private Listener listener;

  public ContentfulInteractor(Context applicationContext) {
    this(applicationContext, Vault.with(applicationContext, VRSpace.class));
  }

  protected ContentfulInteractor(Context applicationContext, Vault vault) {
    this.applicationContext = applicationContext;
    this.vault = vault;
  }

  public void setListener(Listener listener) {
    this.listener = listener;
  }

  public void requestModels() {
    final CDAClient client = CDAClient.builder()
        .setSpace(VRSpace.SPACE_ID)
        .setToken(VRSpace.ACCESS_TOKEN)
        .build();

    vault.requestSync(client);

    Vault.observeSyncResults()
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .flatMap(new Func1<SyncResult, Observable<Product>>() {
          @Override public Observable<Product> call(SyncResult syncResult) {
            return vault.observe(Product.class).order(Product$Fields.NAME).all();
          }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .flatMap(new CreateProtoModelAndRenderSign(applicationContext))
        .flatMap(new DownloadMeshAsset(applicationContext))
        .flatMap(new DownloadTextureAsset(applicationContext))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new EnqueueProtoModel());
  }

  private class EnqueueProtoModel extends Subscriber<ProtoModel> {

    private final String LOG_TAG = EnqueueProtoModel.class.getCanonicalName();

    @Override public void onCompleted() {
    }

    @Override public void onError(Throwable e) {
      Log.e(LOG_TAG, "Error while fetching products!", e);

      if (listener != null) {
        listener.onError(e);
      }
    }

    @Override public void onNext(ProtoModel next) {
      if (listener != null) {
        listener.onModelReceived(next);
      }
    }
  }

}
