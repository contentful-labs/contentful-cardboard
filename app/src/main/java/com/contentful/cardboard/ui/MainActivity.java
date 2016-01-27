package com.contentful.cardboard.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import com.contentful.cardboard.R;
import com.contentful.cardboard.mvpr.Presenter;
import com.contentful.cardboard.mvpr.Renderer;
import com.contentful.cardboard.mvpr.View;
import com.contentful.cardboard.vault.ContentfulInteractor;
import com.contentful.cardboard.vr.CardboardRenderer;
import com.contentful.cardboard.vr.actor.MoveByActor;
import com.contentful.cardboard.vr.actor.ParallelActor;
import com.contentful.cardboard.vr.actor.RotateByActor;
import com.contentful.cardboard.vr.model.Model;
import com.contentful.cardboard.vr.model.ProtoModel;
import com.google.vrtoolkit.cardboard.CardboardActivity;
import com.google.vrtoolkit.cardboard.CardboardView;
import com.google.vrtoolkit.cardboard.Eye;
import com.google.vrtoolkit.cardboard.HeadTransform;
import com.google.vrtoolkit.cardboard.Viewport;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.microedition.khronos.egl.EGLConfig;

public class MainActivity
    extends CardboardActivity
    implements CardboardView.StereoRenderer, View {

  private static final String TAG = "MainActivity";
  private static final String LOG_TAG = MainActivity.class.getCanonicalName();

  private static final float Z_NEAR = 1.0f;
  private static final float Z_FAR = 1000.0f;

  private final ParallelActor actors = new ParallelActor();

  private Vibrator vibrator;
  private Renderer renderer;

  private Presenter presenter;
  private long lastRendered;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.common_ui);

    CardboardView cardboardView = (CardboardView) findViewById(R.id.cardboard_view);
    cardboardView.setRestoreGLStateEnabled(false);
    cardboardView.setRenderer(this);
    setCardboardView(cardboardView);

    vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    renderer = new CardboardRenderer(getApplicationContext());

    presenter = new Presenter(new ContentfulInteractor(this));
    presenter.bind(this);
  }

  @Override protected void onDestroy() {
    super.onDestroy();

    presenter.unbind();
  }

  @Override
  public void onRendererShutdown() {
    Log.i(TAG, "onRendererShutdown");
  }

  @Override
  public void onSurfaceChanged(int width, int height) {
    Log.i(TAG, "onSurfaceChanged");
  }

  @Override
  public void onSurfaceCreated(EGLConfig config) {
    Log.i(TAG, "onSurfaceCreated");
    renderer.surfaceCreated();
  }

  @Override
  public void onNewFrame(HeadTransform headTransform) {
    checkIfAllProductsAreAnimated();

    long currentNanos = System.nanoTime();
    if (lastRendered == 0) {
      lastRendered = System.nanoTime();
    }

    actors.act(currentNanos - lastRendered);
    lastRendered = currentNanos;

    renderer.prepare();
  }

  @Override
  public void onDrawEye(Eye eye) {
    float[] perspective = eye.getPerspective(Z_NEAR, Z_FAR);
    float[] eyeView = eye.getEyeView();
    renderer.render(perspective, eyeView);
  }

  @Override
  public void onFinishFrame(Viewport viewport) {
  }

  @Override
  public void onCardboardTrigger() {
    vibrator.vibrate(25);

    userInteracted();
  }

  private void checkIfAllProductsAreAnimated() {
    for (final Model model : renderer.getForegroundModels()) {
      if (isProductModel(model) && !actors.actsOnModel(model)) {
        actors.add(new RotateByActor(model, -1, 0, 1, 0));
      }
    }
  }

  private boolean isProductModel(Model model) {
    return !model.getName().endsWith(CardboardRenderer.SIGN_POST_FIX);
  }

  private void userInteracted() {
    List<Model> moveableModels = renderer.getForegroundModels();
    if (moveableModels.size() > 1) {
      for (final Model model : moveableModels) {
        if (model.getPosition()[0] > -5.0f) {
          actors.add(new MoveByActor(model, TimeUnit.MILLISECONDS.toNanos(500), -4.0f, 0.0f, 0.0f));
        } else {
          model.moveTo(moveableModels.size() * 4.0f, model.getPosition()[1], model.getPosition()[2]);
        }
      }
    }
  }

  @Override public void addProtoModel(ProtoModel protomodel) {
    renderer.addProtoModel(protomodel);
  }

  @Override public void showError(Throwable throwable) {
    Log.e(LOG_TAG, "ERROR!", throwable);
    Toast.makeText(this, throwable.toString(), Toast.LENGTH_SHORT).show();
  }
}
