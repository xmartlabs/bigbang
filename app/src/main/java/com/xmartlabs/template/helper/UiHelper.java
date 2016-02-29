package com.xmartlabs.template.helper;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Created by santiago on 04/09/15.
 */
public class UiHelper {
  public static boolean checkGooglePlayServicesAndShowAlertIfNeeded(@NonNull Activity activity) {
    int googlePlayServicesStatus = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(activity);

    if (googlePlayServicesStatus == ConnectionResult.SUCCESS) {
      return true;
    }

    if (GoogleApiAvailability.getInstance().isUserResolvableError(googlePlayServicesStatus)) {
      GoogleApiAvailability.getInstance().getErrorDialog(activity, googlePlayServicesStatus, 0).show();
    }

    return false;
  }

  @NonNull
  @SuppressWarnings("unused")
  public static <T> T getSpinnerValue(@NonNull Spinner spinner, @NonNull ArrayAdapter<T> adapter) {
    int selectedPosition = spinner.getSelectedItemPosition();
    return adapter.getItem(selectedPosition);
  }
}
