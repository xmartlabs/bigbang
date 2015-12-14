package com.scottruth.timeoffandroid.helper;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * Created by santiago on 04/09/15.
 */
public class UiHelper {
    public static boolean checkGooglePlayServicesAndShowAlertIfNeeded(@NonNull Activity activity) {
        int googlePlayServicesStatus = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);

        if (googlePlayServicesStatus == ConnectionResult.SUCCESS) {
            return true;
        }

        if (GooglePlayServicesUtil.isUserRecoverableError(googlePlayServicesStatus)) {
            GooglePlayServicesUtil.getErrorDialog(googlePlayServicesStatus, activity, 0).show();
        }

        return false;
    }

    @NonNull
    public static <T> T getSpinnerValue(@NonNull Spinner spinner, @NonNull ArrayAdapter<T> adapter) {
        int selectedPosition = spinner.getSelectedItemPosition();
        return adapter.getItem(selectedPosition);
    }
}
