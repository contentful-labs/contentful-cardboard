package com.contentful.cardboard.vault.util;

import android.content.Context;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Downloader {
  private static final String LOG_TAG = Downloader.class.getCanonicalName();

  public static ByteArrayOutputStream download(Context context, String assetUrl) {
    ByteArrayOutputStream output = null;
    InputStream input = null;
    HttpURLConnection connection = null;

    try {
      input = getCacheInputStream(context, assetUrl);
      boolean isCached = input != null;
      if (!isCached) {
        URL url = new URL(assetUrl);
        connection = (HttpURLConnection) url.openConnection();
        connection.connect();

        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
          throw new IOException("Server returned " + connection.getResponseCode() + ": " + connection.getResponseMessage());
        }

        input = connection.getInputStream();
      }

      output = new ByteArrayOutputStream();

      byte data[] = new byte[4096];
      int count;
      while ((count = input.read(data)) != -1) {
        output.write(data, 0, count);
      }

      if (!isCached) {
        cacheDownload(context, assetUrl, output);
      }
    } catch (MalformedURLException e) {
      Log.e(LOG_TAG, "Asset URL is malformed!");
    } catch (IOException e) {
      Log.e(LOG_TAG, "Could not download file.");
    } finally {
      try {
        if (output != null)
          output.close();
        if (input != null)
          input.close();
      } catch (IOException ignored) {
      }

      if (connection != null) {
        connection.disconnect();
      }
    }

    return output;
  }

  private static void cacheDownload(Context context, String assetUrl, ByteArrayOutputStream content) {
    final File externalDir = context.getExternalCacheDir();
    final String shortAssetName = getShortAssetName(assetUrl);
    final File file = new File(externalDir.getPath(), shortAssetName);

    FileOutputStream stream = null;
    try {
      stream = new FileOutputStream(file, false);
      stream.write(content.toByteArray());
    } catch (FileNotFoundException e) {
      Log.e(LOG_TAG, "Could not find file: \"" + shortAssetName + "\"", e);
    } catch (IOException e) {
      Log.e(LOG_TAG, "", e);
    } finally {
      if (stream != null) {
        try {
          stream.close();
        } catch (IOException ignore) {
        }
      }
    }
  }

  private static InputStream getCacheInputStream(Context context, String assetUrl) {
    final String shortName = getShortAssetName(assetUrl);

    final File external = context.getExternalCacheDir();
    InputStream input = null;
    if (external != null) {
      File[] files = external.listFiles(new FilenameFilter() {
        @Override public boolean accept(File dir, String filename) {
          return shortName.equals(filename);
        }
      });

      if (files.length == 1 && files[0].canRead()) {
        try {
          input = new FileInputStream(files[0]);
        } catch (FileNotFoundException e) {
          Log.e(LOG_TAG, "Could not open \"" + shortName + "\"…", e);
        }
      }
    }

    return input;
  }

  private static String getShortAssetName(String assetUrl) {
    final String[] separatorSplit = assetUrl.split("/");
    return separatorSplit[separatorSplit.length - 1];
  }
}
