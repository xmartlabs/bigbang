package com.xmartlabs.bigbang.core.helper;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

@SuppressWarnings("unused")
public class SystemServiceHelper {
  /**
   * Uses the {@link ConnectivityManager} class to determine whether the device is connected to the internet or not.
   *
   * @param context the {@link Context} from which to get the CONNECTIVITY_SERVICE
   * @return true if the device is connected to the internet
   */
  public static boolean hasNetworkConnection(@NonNull Context context) {
    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(context, Context.CONNECTIVITY_SERVICE);
    return connectivityManager.getActiveNetworkInfo() != null &&
        connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
  }

  /**
   * Copies the {@code text} to the clipboard by means of the {@link ClipboardManager}.
   *
   * @param context the {@link Context} from which to get the CLIPBOARD_SERVICE
   * @param text the text to be copied
   */
  @SuppressLint("ObsoleteSdkInt")
  public static void copyTextToClipboard(@NonNull Context context, @NonNull String text) {
    if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
      //noinspection deprecation
      android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(context, Context.CLIPBOARD_SERVICE);
      clipboard.setText(text);
    } else {
      android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(context, Context.CLIPBOARD_SERVICE);
      android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
      clipboard.setPrimaryClip(clip);
    }
  }

  private static Object getSystemService(@NonNull Context context, @NonNull String clipboardService) {
    return context.getSystemService(clipboardService);
  }
}
