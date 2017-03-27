package com.xmartlabs.template.helper.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.annotation.StringRes;

import com.xmartlabs.template.BaseProjectApplication;
import com.xmartlabs.template.helper.StringUtils;

import lombok.val;

@SuppressWarnings("unused")
public class IntentHelper {
  /**
   * Creates an intent to make phone calls.
   *
   * @param phoneNumber the number to call
   * @return the created intent
   */
  @NonNull
  @RequiresPermission(Manifest.permission.CALL_PHONE)
  public static Intent getMakeAPhoneCallIntent(@NonNull String phoneNumber) {
    val intent = new Intent(Intent.ACTION_CALL);
    intent.setData(Uri.parse("tel:" + phoneNumber));
    return intent;
  }

  /**
   * Creates an intent to send SMS messages.
   *
   * @param phoneNumber the number to send the message to
   * @param chooserTitle the title of the app chooser
   * @return the created intent
   */
  @NonNull
  public static Intent getSendMessageIntent(@NonNull String phoneNumber, String chooserTitle) {
    val intent = new Intent(Intent.ACTION_SENDTO);
    intent.setData(Uri.parse("smsto:" + phoneNumber));
    return Intent.createChooser(intent, chooserTitle);
  }

  /**
   * Creates an intent to send emails.
   *
   * @param email the email address to send the email to
   * @param chooserTitle the title of the app chooser
   * @return the created intent
   */
  @NonNull
  public static Intent getSendEmailIntent(@NonNull String email, String chooserTitle) {
    return getSendEmailIntent(email, null, null, chooserTitle);
  }

  /**
   * Creates an intent to send emails.
   *
   * @param email the email address to send the email to
   * @param subject the subject of the email
   * @param body the body of the email
   * @param chooserTitle the title of the app chooser
   * @return the created intent
   */
  @NonNull
  public static Intent getSendEmailIntent(@NonNull String email, @Nullable String subject, @Nullable String body,
                                          @Nullable String chooserTitle) {
    return getSendEmailIntent(email, subject, body, null, chooserTitle);
  }

  /**
   * Creates an intent to send emails, including a file.
   *
   * @param email the email address to send the email to
   * @param subject the subject of the email
   * @param body the body of the email
   * @param filePath the path of the file to append
   * @param chooserTitle the title of the app chooser
   * @return the created intent
   */
  @NonNull
  public static Intent getSendEmailIntent(@NonNull String email, @Nullable String subject, @Nullable String body,
                                          @Nullable String filePath, @Nullable String chooserTitle) {
    val intent = new Intent(Intent.ACTION_SEND);
    intent.setType("message/rfc822");
    intent.putExtra(Intent.EXTRA_EMAIL, new String[] {email});
    if (!StringUtils.isNullOrEmpty(subject)) {
      intent.putExtra(Intent.EXTRA_SUBJECT, subject);
    }
    if (!StringUtils.isNullOrEmpty(body)) {
      intent.putExtra(Intent.EXTRA_TEXT, subject);
    }

    if (!StringUtils.isNullOrEmpty(filePath)) {
      intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + filePath));
    }

    return Intent.createChooser(intent, chooserTitle);
  }

  /**
   * Checks if there's any app that can handle the {@link Intent}.
   *
   * @param intent the intent to check
   * @return true if there's an app that can handle the {@code intent}
   */
  public static boolean canDeviceHandleIntent(@NonNull Intent intent) {
    return BaseProjectApplication.getContext()
        .getPackageManager().queryIntentActivities(intent, 0).size() > 0;
  }

  /**
   * Checks whether or not the device has a camera.
   *
   * @return true if the device has a camera
   */
  public static boolean deviceHasCameraAbility() {
    return BaseProjectApplication.getContext()
        .getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
  }

  /**
   * Retrieves the string resource with {@code messageRes} id.
   *
   * @param messageRes the id of the string resource
   * @return the retrieved string
   */
  @NonNull
  private static String getString(@StringRes int messageRes) {
    return BaseProjectApplication.getContext().getResources().getString(messageRes);
  }
}
