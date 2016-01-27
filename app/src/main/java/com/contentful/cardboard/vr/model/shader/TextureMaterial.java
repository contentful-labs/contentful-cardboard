package com.contentful.cardboard.vr.model.shader;

import android.content.Context;
import android.graphics.Bitmap;

import com.contentful.cardboard.R;
import com.contentful.cardboard.vr.model.mesh.Mesh;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_NEAREST;
import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDisableVertexAttribArray;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGenTextures;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glTexParameteri;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLUtils.texImage2D;
import static com.contentful.cardboard.vr.util.ErrorUtils.checkGLError;
import static com.contentful.cardboard.vr.util.ShaderUtils.loadGLShader;

public class TextureMaterial extends Material {
  private final int textureId;
  private final int uvsParameter;

  private final int textureDataParameter;

  public TextureMaterial(Context context, Bitmap bitmap) {
    super(loadGLShader(context, GL_VERTEX_SHADER, R.raw.vertex),
        loadGLShader(context, GL_FRAGMENT_SHADER, R.raw.fog_texture_fragment));

    this.uvsParameter = glGetAttribLocation(program, "a_TexCoordinate");
    this.textureDataParameter = glGetUniformLocation(program, "u_Texture");

    textureId = createTextureFromBitmap(bitmap);
    checkGLError("Creating a texture");
  }

  @Override
  public void setup(float[] model, float[] modelView, float[] modelViewProjection, float[] view, float[] camera, Mesh mesh) {
    super.setup(model, modelView, modelViewProjection, view, camera, mesh);
    glVertexAttribPointer(uvsParameter, 2, GL_FLOAT, false, 0, mesh.getUvs());

    glEnableVertexAttribArray(uvsParameter);
    checkGLError("Enable texture material parameter");

    glActiveTexture(GL_TEXTURE0);
    glBindTexture(GL_TEXTURE_2D, textureId);
    glUniform1i(textureDataParameter, 0);

    checkGLError("Setting up additional parameter for textured material");
  }

  @Override
  public void tearDown() {
    super.tearDown();

    glDisableVertexAttribArray(uvsParameter);

    glBindTexture(GL_TEXTURE_2D, 0);
  }

  private int createTextureFromBitmap(Bitmap bitmap) {
    final int[] textureHandle = new int[1];

    glGenTextures(1, textureHandle, 0);

    if (textureHandle[0] != 0) {
      glBindTexture(GL_TEXTURE_2D, textureHandle[0]);

      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
      checkGLError("setting texture parameter...");

      texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
      checkGLError("uploading image data...");

      bitmap.recycle();
    }

    if (textureHandle[0] == 0) {
      throw new RuntimeException("Error loading texture.");
    }

    return textureHandle[0];
  }

}
