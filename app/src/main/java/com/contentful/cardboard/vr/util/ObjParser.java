package com.contentful.cardboard.vr.util;

import android.util.Log;

import com.contentful.cardboard.vr.model.mesh.Mesh;

import java.util.Vector;

import static com.contentful.cardboard.vr.util.BufferUtils.floatArrayToNativeFloatBuffer;

public class ObjParser {

  private static final String LOG_TAG = ObjParser.class.getCanonicalName();

  Vector<Integer> faces = new Vector<>();

  Vector<Float> v = new Vector<>();
  Vector<Float> vn = new Vector<>();
  Vector<Float> vt = new Vector<>();

  public Mesh loadMeshFromString(String source) {
    String[] lines = source.split("\n");

    for (final String line : lines) {
      if (line.startsWith("f")) {//a polygonal face
        processFLine(line);
      } else if (line.startsWith("vn")) {
        processVNLine(line);
      } else if (line.startsWith("vt")) {
        processVTLine(line);
      } else if (line.startsWith("v")) { //line having geometric position of single vertex
        processVLine(line);
      }
    }

    return createMeshFromFaces();
  }

  private Mesh createMeshFromFaces() {
    final float[] vertices = new float[faces.size()];
    final float[] normals = new float[faces.size()];
    final float[] uvs = new float[faces.size() / 3 * 2];

    for (int i = 0; i < faces.size() / 3; ++i) {
      Integer vertexIndex = faces.get(i * 3 + 0);
      vertices[i * 3 + 0] = v.get(vertexIndex * 3 + 0);
      vertices[i * 3 + 1] = v.get(vertexIndex * 3 + 1);
      vertices[i * 3 + 2] = v.get(vertexIndex * 3 + 2);

      Integer uvIndex = faces.get(i * 3 + 1);
      uvs[i * 2 + 0] = vt.get(uvIndex * 2 + 0);
      uvs[i * 2 + 1] = vt.get(uvIndex * 2 + 1);

      Integer normalIndex = faces.get(i * 3 + 2);
      normals[i * 3 + 0] = vn.get(normalIndex * 3 + 0);
      normals[i * 3 + 1] = vn.get(normalIndex * 3 + 1);
      normals[i * 3 + 2] = vn.get(normalIndex * 3 + 2);
    }

    return new Mesh(floatArrayToNativeFloatBuffer(vertices), floatArrayToNativeFloatBuffer(normals), floatArrayToNativeFloatBuffer(uvs));
  }


  private void processVLine(String line) {
    String[] tokens = line.split("[ ]+"); //split the line at the spaces
    int c = tokens.length;
    for (int i = 1; i < c; i++) { //add the vertex to the vertex array
      v.add(Float.valueOf(tokens[i]));
    }
  }

  private void processVNLine(String line) {
    String[] tokens = line.split("[ ]+"); //split the line at the spaces
    int c = tokens.length;
    for (int i = 1; i < c; i++) { //add the vertex to the vertex array
      vn.add(Float.valueOf(tokens[i]));
    }
  }

  private void processVTLine(String line) {
    String[] tokens = line.split("[ ]+"); //split the line at the spaces
    vt.add(Float.valueOf(tokens[1]));
    vt.add(1.0f - Float.valueOf(tokens[2]));
  }

  private void processFLine(String line) {
    String[] tokens = line.split("[ ]+");
    int c = tokens.length;

    if (tokens[1].matches("[0-9]+/[0-9]+/[0-9]+")) {//f: v/vt/vn
      if (c == 4) {//3 faces
        for (int i = 1; i < c; i++) {
          Integer s = Integer.valueOf(tokens[i].split("/")[0]);
          s--;
          faces.add(s);
          s = Integer.valueOf(tokens[i].split("/")[1]);
          s--;
          faces.add(s);
          s = Integer.valueOf(tokens[i].split("/")[2]);
          s--;
          faces.add(s);
        }
      } else {
        Log.e(LOG_TAG, "Non Triangle face found, please reexport mesh file with triangulated faces.");
      }
    } else {
      Log.e(LOG_TAG, "Face not in v/vt/vn form given! Please try again.");
    }
  }
}
