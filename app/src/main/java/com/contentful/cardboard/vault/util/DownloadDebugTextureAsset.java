package com.contentful.cardboard.vault.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.contentful.cardboard.vr.model.ProtoModel;

import rx.Observable;
import rx.functions.Func1;

public class DownloadDebugTextureAsset implements Func1<ProtoModel, Observable<ProtoModel>> {

  public static final int SIZE = 32;
  public static final int CENTER = SIZE / 2;

  private final Paint textPaint;
  private final int textHeight;

  public DownloadDebugTextureAsset() {
    textPaint = new Paint();

    textPaint.setColor(Color.BLACK);
    textPaint.setTextAlign(Paint.Align.CENTER);
    textPaint.setAntiAlias(true);

    final Rect textBounds = new Rect();
    textPaint.getTextBounds("DEBUG", 0, "DEBUG".length(), textBounds);

    textHeight = textBounds.height();
  }

  @Override public Observable<ProtoModel> call(ProtoModel protoModel) {
    final Bitmap bitmap = Bitmap.createBitmap(SIZE, SIZE, Bitmap.Config.ARGB_8888);
    final Canvas canvas = new Canvas(bitmap);
    canvas.drawColor(Color.WHITE);

    canvas.rotate(45, CENTER, CENTER);
    canvas.drawText("DEBUG", CENTER, CENTER - textHeight / 2, textPaint);
    protoModel.texture = bitmap;

    return Observable.just(protoModel);
  }
}
