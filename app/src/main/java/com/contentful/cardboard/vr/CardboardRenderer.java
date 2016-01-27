package com.contentful.cardboard.vr;

import android.content.Context;

import com.contentful.cardboard.mvpr.Renderer;
import com.contentful.cardboard.vr.model.Model;
import com.contentful.cardboard.vr.model.ProtoModel;
import com.contentful.cardboard.vr.model.mesh.CubeMesh;
import com.contentful.cardboard.vr.model.mesh.Mesh;
import com.contentful.cardboard.vr.model.mesh.QuadMesh;
import com.contentful.cardboard.vr.model.shader.FloorMaterial;
import com.contentful.cardboard.vr.model.shader.Material;
import com.contentful.cardboard.vr.model.shader.TextureMaterial;

import java.util.ArrayList;
import java.util.List;

import static android.opengl.GLES20.GL_BLEND;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_TEST;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_ONE_MINUS_SRC_ALPHA;
import static android.opengl.GLES20.GL_SRC_ALPHA;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glBlendFunc;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.setLookAtM;
import static com.contentful.cardboard.vr.util.ErrorUtils.checkGLError;

public class CardboardRenderer implements Renderer {

  public static final String SIGN_POST_FIX = " sign";

  private static final float CAMERA_Z = 0.01f;

  private final float[] camera = new float[16];
  private final float[] view = new float[16];

  private final float[] modelViewProjection = new float[16];
  private final float[] modelView = new float[16];

  private final List<Model> backgroundModels = new ArrayList<>();
  private final List<Model> foregroundModels = new ArrayList<>();
  private final List<ProtoModel> protoModelQueue = new ArrayList<>();

  private final Context applicationContext;

  private FloorMaterial floorMaterial;

  public CardboardRenderer(Context applicationContext) {
    this.applicationContext = applicationContext;
  }

  @Override public void surfaceCreated() {
    addFloorAndCeiling();
  }

  @Override public void prepare() {
    addPendingModels();
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    glClearColor(0x00, 0x00, 0x00, 0xFF);

    setLookAtM(camera, 0, 0.0f, 0.0f, CAMERA_Z, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
    checkGLError("onReadyToDraw");
  }

  @Override
  public void render(float[] perspective, float[] eyeView) {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    checkGLError("colorParam");

    multiplyMM(view, 0, eyeView, 0, camera, 0);

    for (final Model model : backgroundModels) {
      renderModel(perspective, model);
    }

    for (final Model model : foregroundModels) {
      renderModel(perspective, model);
    }
  }

  @Override public void addProtoModel(ProtoModel protoModel) {
    if (protoModel != null) {
      protoModelQueue.add(protoModel);
    }
  }

  private void addFloorAndCeiling() {
    float size = 400;
    CubeMesh cubeMesh = new CubeMesh();
    if (floorMaterial == null) {
      floorMaterial = new FloorMaterial(applicationContext);
    }

    backgroundModels.add(new Model("floor", cubeMesh, floorMaterial)
        .moveBy(0.0f, -10.0f, 0.0f)
        .scaleBy(size, .1f, size));
    backgroundModels.add(new Model("ceiling", cubeMesh, floorMaterial)
        .moveBy(0.0f, 10.0f, 0.0f)
        .scaleBy(size, .1f, size));
  }

  private void renderModel(float[] perspective, Model model) {
    float[] modelMatrix = model.createModelMatrix();
    multiplyMM(modelView, 0, view, 0, modelMatrix, 0);
    multiplyMM(modelViewProjection, 0, perspective, 0, modelView, 0);

    Material material = model.getMaterial();
    Mesh mesh = model.getMesh();
    material.setup(
        modelMatrix, modelView, modelViewProjection,
        view,
        camera,
        mesh);

    glVertexAttribPointer(material.getPositionParameter(), 3, GL_FLOAT, false, 0, mesh.getVertices());
    checkGLError("Position parameter");
    glVertexAttribPointer(material.getNormalParameter(), 3, GL_FLOAT, false, 0, mesh.getNormals());
    checkGLError("Normal parameter");

    glDrawArrays(GL_TRIANGLES, 0, mesh.getVertices().limit() / 3);
    checkGLError("Drawing mesh \"" + mesh.toString() + "\"");

    material.tearDown();
  }

  private void addPendingModels() {
    for (ProtoModel protoModel : protoModelQueue) {
      Model product = new Model(protoModel.name,
          protoModel.mesh,
          new TextureMaterial(applicationContext, protoModel.texture))
          .moveBy(protoModel.position)
          .scaleBy(.8f, .8f, .8f);
      foregroundModels.add(product);

      Model sign = new Model(protoModel.name + SIGN_POST_FIX,
          new QuadMesh(),
          new TextureMaterial(applicationContext, protoModel.signTexture)
      )
          .moveBy(protoModel.position)
          .scaleBy(1.8f, 1.8f, 1f)
          .rotateBy(protoModel.rotation);
      foregroundModels.add(sign);
    }

    protoModelQueue.clear();
  }

  public List<Model> getForegroundModels() {
    return foregroundModels;
  }

  public List<Model> getBackgroundModels() {
    return backgroundModels;
  }
}
