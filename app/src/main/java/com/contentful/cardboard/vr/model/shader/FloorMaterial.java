package com.contentful.cardboard.vr.model.shader;

import android.content.Context;

import com.contentful.cardboard.R;

import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static com.contentful.cardboard.vr.util.ShaderUtils.loadGLShader;

public class FloorMaterial extends VertexLightingMaterial {
  public FloorMaterial(Context context) {
    super(loadGLShader(context, GL_VERTEX_SHADER, R.raw.vertex),
        loadGLShader(context, GL_FRAGMENT_SHADER, R.raw.grid_fog_fragment)
    );
  }
}
