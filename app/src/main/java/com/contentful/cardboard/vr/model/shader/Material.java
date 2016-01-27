package com.contentful.cardboard.vr.model.shader;

import com.contentful.cardboard.vr.model.mesh.Mesh;

import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glIsProgram;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static com.contentful.cardboard.vr.util.ErrorUtils.checkGLError;

public class Material {

  protected final int program;

  private final int positionParameter;
  private final int normalParameter;

  private final int modelParameter;

  private final int modelViewParameter;
  private final int modelViewProjectionParameter;


  protected Material(int vertexShader, int fragmentShader) {
    program = glCreateProgram();
    glAttachShader(program, vertexShader);
    glAttachShader(program, fragmentShader);
    glLinkProgram(program);
    glUseProgram(program);

    checkGLError("error building program \"" + toString() + "\" program.");

    this.positionParameter = glGetAttribLocation(program, "a_Position");
    this.normalParameter = glGetAttribLocation(program, "a_Normal");

    this.modelParameter = glGetUniformLocation(program, "u_Model");
    this.modelViewParameter = glGetUniformLocation(program, "u_MVMatrix");
    this.modelViewProjectionParameter = glGetUniformLocation(program, "u_MVP");
    checkGLError("error binding shader parameters of \"" + toString() + "\" program.");

    glEnableVertexAttribArray(positionParameter);
    checkGLError("could not enable position attributes for \"" + toString() + "\" program");

    glEnableVertexAttribArray(normalParameter);
    checkGLError("could not enable normal attributes for \"" + toString() + "\" program");
  }

  public void setup(float[] model, float[] modelView, float[] modelViewProjection,
                    float[] view, float[] camera, Mesh mesh) {
    if (!glIsProgram(program)) {
      throw new IllegalStateException("This is not a valid program!");
    }
    glUseProgram(program);

    glUniformMatrix4fv(modelParameter, 1, false, model, 0);
    glUniformMatrix4fv(modelViewParameter, 1, false, modelView, 0);
    glUniformMatrix4fv(modelViewProjectionParameter, 1, false, modelViewProjection, 0);
  }

  public void tearDown() {
  }

  public int getProgram() {
    return program;
  }

  public int getPositionParameter() {
    return positionParameter;
  }

  public int getNormalParameter() {
    return normalParameter;
  }

  public int getModelParameter() {
    return modelParameter;
  }

  public int getModelViewParameter() {
    return modelViewParameter;
  }

  public int getModelViewProjectionParameter() {
    return modelViewProjectionParameter;
  }

}
