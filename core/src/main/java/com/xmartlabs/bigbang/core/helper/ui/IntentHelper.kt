package com.xmartlabs.bigbang.core.helper.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.annotation.RequiresPermission
import android.support.annotation.StringRes

object IntentHelper {
  /**
   * Creates an intent to make phone calls.
   *
   * @param phoneNumber the number to call
   * *
   * @return the created intent
   */
  @RequiresPermission(value = Manifest.permission.CALL_PHONE)
  fun getMakeAPhoneCallIntent(phoneNumber: String): Intent {
    val intent = Intent(Intent.ACTION_CALL)
    intent.data = Uri.parse("tel:" + phoneNumber)
    return intent
  }

  /**
   * Creates an intent to send SMS messages.
   *
   * @param phoneNumber the number to send the message to
   * *
   * @param chooserTitle the title of the app chooser
   * *
   * @return the created intent
   */
  fun getSendMessageIntent(phoneNumber: String, chooserTitle: String): Intent {
    val intent = Intent(Intent.ACTION_SENDTO)
    intent.data = Uri.parse("smsto:" + phoneNumber)
    return Intent.createChooser(intent, chooserTitle)
  }

  /**
   * Creates an intent to send emails.
   *
   * @param email the email address to send the email to
   * *
   * @param chooserTitle the title of the app chooser
   * *
   * @return the created intent
   */
  fun getSendEmailIntent(email: String, chooserTitle: String) = getSendEmailIntent(email, null, null, chooserTitle)

  /**
   * Creates an intent to send emails.
   *
   * @param email the email address to send the email to
   * *
   * @param subject the subject of the email
   * *
   * @param body the body of the email
   * *
   * @param chooserTitle the title of the app chooser
   * *
   * @return the created intent
   */
  fun getSendEmailIntent(email: String, subject: String?, body: String?, chooserTitle: String?) =
    getSendEmailIntent(email, subject, body, null, chooserTitle)

  /**
   * Creates an intent to send emails, including a file.
   *
   * @param email the email address to send the email to
   * *
   * @param subject the subject of the email
   * *
   * @param body the body of the email
   * *
   * @param filePath the path of the file to append
   * *
   * @param chooserTitle the title of the app chooser
   * *
   * @return the created intent
   */
  fun getSendEmailIntent(email: String, subject: String?, body: String?,
                         filePath: String?, chooserTitle: String?): Intent {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "message/rfc822"
    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
    
    subject?.takeIf(String::isNotEmpty)?.let { intent.putExtra(Intent.EXTRA_SUBJECT, it) }
    body?.takeIf(String::isNotEmpty)?.let { intent.putExtra(Intent.EXTRA_TEXT, it) }
    filePath?.takeIf(String::isNotEmpty)?.let { intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + it)) }

    return Intent.createChooser(intent, chooserTitle)
  }

  /**
   * Checks if there's any app that can handle the [Intent].
   *
   * @param intent the intent to check
   * *
   * @return true if there's an app that can handle the `intent`
   */
  fun canHandleIntent(context: Context, intent: Intent) = context.packageManager.queryIntentActivities(intent, 0).size > 0

  /**
   * Checks whether or not the device has a camera.
   *
   * @return true if the device has a camera
   */
  fun hasCameraAbility(context: Context) = context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)

  /**
   * Retrieves the string resource with `messageRes` id.
   *
   * @param messageRes the id of the string resource
   * *
   * @return the retrieved string
   */
  private fun getString(context: Context, @StringRes messageRes: Int) = context.resources.getString(messageRes)
}
