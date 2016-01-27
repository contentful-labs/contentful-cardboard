package com.contentful.cardboard.vault.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

import com.contentful.cardboard.R;
import com.contentful.cardboard.ui.ProductDisplay;
import com.contentful.cardboard.vault.Product;
import com.contentful.cardboard.vr.model.ProtoModel;

import rx.Observable;
import rx.functions.Func1;

public class CreateProtoModelAndRenderSign implements Func1<Product, Observable<ProtoModel>> {
  private static final int WIDTH = 1024;
  private static final int HEIGHT = 1024;

  private final Context applicationContext;

  public CreateProtoModelAndRenderSign(Context applicationContext) {
    this.applicationContext = applicationContext;
  }

  @Override public Observable<ProtoModel> call(Product product) {
    final ProtoModel result = new ProtoModel();
    result.name = product.name();
    result.signTexture = renderProductSign(product);

    result.meshUrl = product.previewMeshFile().url();
    result.textureUrl = product.textures().get(0).url();

    result.position = new float[]{0.0f, 0.0f, -3.0f, 1.0f};
    result.scale = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
    result.rotation = new float[]{0.0f, 0.0f, 0.0f, 1.0f};

    return Observable.just(result);
  }

  private Bitmap renderProductSign(Product product) {
    final Bitmap bitmap = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.ARGB_8888);
    final Canvas canvas = new Canvas(bitmap);

    int measuredWidth = View.MeasureSpec.makeMeasureSpec(WIDTH, View.MeasureSpec.EXACTLY);
    int measuredHeight = View.MeasureSpec.makeMeasureSpec(HEIGHT, View.MeasureSpec.EXACTLY);

    final ProductDisplay display = (ProductDisplay) View.inflate(applicationContext, R.layout.product_display_layout, null);
    display.update(product);

    display.measure(measuredWidth, measuredHeight);
    display.layout(0, 0, display.getMeasuredWidth(), display.getMeasuredHeight());
    display.draw(canvas);
    return bitmap;
  }

}
