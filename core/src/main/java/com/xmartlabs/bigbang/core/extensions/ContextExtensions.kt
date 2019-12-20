package com.xmartlabs.bigbang.core.extensions

import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.content.Context
import android.net.ConnectivityManager

/**
 * Uses the [ConnectivityManager] class to determine whether the device is connected to the internet or not.
 * *
 * @return true if the device is connected to the internet
 */
fun Context.hasNetworkConnection(): Boolean {
  val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
  return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnectedOrConnecting
}

/**
 * Copies the `text` to the clipboard by means of the [ClipboardManager].
 * *
 * @param text the text to be copied
 */
@SuppressLint("ObsoleteSdkInt")
fun Context.copyTextToClipboard(text: String) {
  if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
    @Suppress("DEPRECATION")
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as android.text.ClipboardManager
    clipboard.text = text
  } else {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
    val clip = android.content.ClipData.newPlainText("Copied Text", text)
    clipboard.setPrimaryClip(clip)
  }
}
