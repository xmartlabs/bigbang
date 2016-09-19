package com.xmartlabs.template.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

import com.xmartlabs.template.BaseProjectApplication;

/**
 * Created by medina on 16/09/2016.
 */
public class SystemServiceHelper {
  public static boolean hasNetworkConnection() {
    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    return connectivityManager.getActiveNetworkInfo() != null &&
        connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
  }

  public static void copyTextToClipboard(@NonNull String text) {
    if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
      //noinspection deprecation
      android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
      clipboard.setText(text);
    } else {
      android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
      android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
      clipboard.setPrimaryClip(clip);
    }
  }

  private static Object getSystemService(String clipboardService) {
    return BaseProjectApplication.getContext().getSystemService(clipboardService);
  }
}