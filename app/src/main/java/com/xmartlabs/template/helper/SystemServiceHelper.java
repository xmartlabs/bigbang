package com.xmartlabs.template.helper;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

import com.xmartlabs.template.BaseProjectApplication;

@SuppressWarnings("unused")
public class SystemServiceHelper {
  /**
   * Uses the {@link ConnectivityManager} class to determine whether the device is connected to the internet or not.
   *
   * @return true if the device is connected to the internet
   */
  public static boolean hasNetworkConnection() {
    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    return connectivityManager.getActiveNetworkInfo() != null &&
        connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
  }

  /**
   * Copies the {@code text} to the clipboard by means of the {@link ClipboardManager}.
   *
   * @param text the text to be copied
   */
  @SuppressLint("ObsoleteSdkInt")
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