package com.xmartlabs.template.helper.ui;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.annimon.stream.Stream;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.xmartlabs.template.R;

import java.util.List;
import java.util.Locale;

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

  @NonNull
  public static String getUrlForDrawable(Context context, @DrawableRes int drawableResId) {
    return String.format(
        Locale.US,
        "%s://%s/%s/%s",
        ContentResolver.SCHEME_ANDROID_RESOURCE,
        context.getResources().getResourcePackageName(drawableResId),
        context.getResources().getResourceTypeName(drawableResId),
        context.getResources().getResourceEntryName(drawableResId)
    );
  }

  public static boolean isValidField(EditText editText) {
    return !editText.getText().toString().isEmpty()
        && (!isEmailField(editText) || isValidEmail(editText.getText()));
  }

  public static boolean isEmailField(EditText editText) {
    return (editText.getInputType() & InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS) ==
        InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
  }

  public static boolean isValidEmail(CharSequence target) {
    return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
  }

  @Nullable
  public static TextInputLayout getTextInputLayout(EditText editText) {
    if (editText.getParent() instanceof TextInputLayout) {
      return (TextInputLayout) editText.getParent();
    } else if (editText.getParent().getParent() instanceof TextInputLayout) {
      return (TextInputLayout) editText.getParent().getParent();
    }
    return null;
  }

  public static void validateFieldsAndPutErrorMessages(List<EditText> fields, boolean hideErrorLayout) {
    Stream.of(fields)
        .forEach(field -> UiHelper.validateField(field, hideErrorLayout));
  }

  public static boolean validateField(EditText editText, boolean hideErrorLayout) {
    boolean isValid = UiHelper.isValidField(editText);
    TextInputLayout textInputLayout = UiHelper.getTextInputLayout(editText);
    if (textInputLayout != null) {
      if (isValid) {
        textInputLayout.setError(null);
        textInputLayout.setErrorEnabled(!hideErrorLayout);
      } else {
        textInputLayout.setErrorEnabled(true);
        textInputLayout.setError(UiHelper.isEmailField(editText)
            ? editText.getResources().getString(R.string.the_email_not_valid)
            : editText.getResources().getString(R.string.field_required)
        );
      }
    }
    return isValid;
  }
}
