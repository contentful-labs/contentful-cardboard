package com.contentful.cardboard.vr.util;

import android.util.Log;

import static android.opengl.GLES20.GL_NO_ERROR;
import static android.opengl.GLES20.glGetError;
import static android.opengl.GLU.gluErrorString;

public class ErrorUtils {
  private static final String TAG = ErrorUtils.class.getCanonicalName();

  /**
   * Checks if we've had an error inside of OpenGL ES, and if so what that error is.
   *
   * @param label Label to report in case of error.
   */
  public static void checkGLError(String label) {
    final int error;
    if ((error = glGetError()) != GL_NO_ERROR) {
      String message = label + "(#" + error + ": " + gluErrorString(error) + ")";
      Log.e(TAG, message);

      throw new RuntimeException(message);
    }
  }
}
