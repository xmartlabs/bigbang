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
import com.xmartlabs.template.R;
import com.xmartlabs.template.helper.StringUtils;

import lombok.val;

/**
 * Created by medina on 19/09/2016.
 */
public class IntentHelper {
  @NonNull
  @RequiresPermission(Manifest.permission.CALL_PHONE)
  public static Intent getMakeAPhoneCallIntent(@NonNull String phoneNumber) {
    val intent = new Intent(Intent.ACTION_CALL);
    intent.setData(Uri.parse("tel:" + phoneNumber));
    return intent;
  }

  @NonNull
  public static Intent getSendMessageIntent(@NonNull String phoneNumber, String chooserTitle) {
    val intent = new Intent(Intent.ACTION_SENDTO);
    intent.setData(Uri.parse("smsto:" + phoneNumber));
    return Intent.createChooser(intent, chooserTitle);
  }

  @NonNull
  public static Intent getSendEmailIntent(@NonNull String email, @Nullable String subject, @Nullable String body,
                                          String chooserTitle) {
    return getSendEmailIntent(email, subject, body, null, chooserTitle);
  }

  @NonNull
  public static Intent getSendEmailIntent(@NonNull String email, @Nullable String subject, @Nullable String body,
                                          @Nullable String filePath, String chooserTitle) {
    val intent = new Intent(Intent.ACTION_SEND);
    intent.setType("message/rfc822");
    intent.putExtra(Intent.EXTRA_EMAIL, new String[] {email});
    if (!StringUtils.stringIsNullOrEmpty(subject)) {
      intent.putExtra(Intent.EXTRA_SUBJECT, subject);
    }
    if (!StringUtils.stringIsNullOrEmpty(body)) {
      intent.putExtra(Intent.EXTRA_TEXT, subject);
    }

    if (!StringUtils.stringIsNullOrEmpty(filePath)) {
      intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + filePath));
    }

    return Intent.createChooser(intent, chooserTitle);
  }

  @NonNull
  public static Intent getSendEmailIntent(@NonNull String email, String chooserTitle) {
    return getSendEmailIntent(email, null, null, chooserTitle);
  }

  public static boolean deviceHasAbility(@NonNull Intent intent) {
    return BaseProjectApplication.getContext()
        .getPackageManager().queryIntentActivities(intent, 0).size() > 0;
  }

  public static boolean deviceHasCameraAbility() {
    return BaseProjectApplication.getContext()
        .getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
  }

  @NonNull
  private static String getString(@StringRes int messageRes) {
    return BaseProjectApplication.getContext().getResources().getString(messageRes);
  }
}
