package com.contentful.cardboard.vr.model.shader;

import android.opengl.Matrix;

import com.contentful.cardboard.vr.model.mesh.Mesh;

import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform3fv;
import static com.contentful.cardboard.vr.util.ErrorUtils.checkGLError;

public class VertexLightingMaterial extends Material {
  private static final float[] LIGHT_POS_IN_WORLD_SPACE = new float[]{0, 10, 0, 1};
  private static final float[] COLOR = new float[]{0.5f, 0.5f, 0.5f};

  private final float[] lightPositionInEyeSpace = new float[4];

  private final int lightPositionParameter;
  private final int lightColorParameter;

  protected VertexLightingMaterial(int vertexShader, int fragmentShader) {
    super(vertexShader, fragmentShader);

    lightPositionParameter = glGetUniformLocation(program, "u_LightPosition");
    lightColorParameter = glGetUniformLocation(program, "u_LightColor");

    checkGLError("could not bind light position parameter \"" + toString() + "\") program");
  }

  @Override
  public void setup(float[] model, float[] modelView, float[] modelViewProjection, float[] view, float[] camera, Mesh mesh) {
    super.setup(model, modelView, modelViewProjection, view, camera, mesh);

    // Set the position of the light
    Matrix.multiplyMV(lightPositionInEyeSpace, 0, view, 0, LIGHT_POS_IN_WORLD_SPACE, 0);

    glUniform3fv(lightPositionParameter, 1, lightPositionInEyeSpace, 0);
    glUniform3fv(lightColorParameter, 1, COLOR, 0);
  }
}
