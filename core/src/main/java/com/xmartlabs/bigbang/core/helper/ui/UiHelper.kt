package com.xmartlabs.bigbang.core.helper.ui

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.support.annotation.DrawableRes
import android.support.design.widget.TextInputLayout
import android.support.v4.util.Pair
import android.text.InputType
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import java.util.Locale

object UiHelper {
  /**
   * Displays an alert if the Google Play Services are not enabled.
   *
   * @param activity the activity to use to check the availability of the Google Play Services
   * *
   * @return true if the Google Play Services are available
   */
  fun checkGooglePlayServicesAndShowAlertIfNeeded(activity: Activity): Boolean {
    val googlePlayServicesStatus = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(activity)
    
    if (googlePlayServicesStatus == ConnectionResult.SUCCESS) {
      return true
    }
    
    if (GoogleApiAvailability.getInstance().isUserResolvableError(googlePlayServicesStatus)) {
      GoogleApiAvailability.getInstance().getErrorDialog(activity, googlePlayServicesStatus, 0).show()
    }
    
    return false
  }
  
  /**
   * Retrieves the object in the `adapter` at the `spinner` position.
   *
   * @param spinner the spinner to retrieve the position from
   * *
   * @param adapter the adapter to retrieve the object from
   * *
   * @param <T> the type of the object held by the adapter
   * *
   * @return an instance of type T at the spinner position
  </T> */
  fun <T> getSpinnerValue(spinner: Spinner, adapter: ArrayAdapter<T>) = adapter.getItem(spinner.selectedItemPosition)
  
  /**
   * Retrieves the URL of the drawable with `drawableResId` id in the given `context`.
   *
   * @param context the context from which to retrieve the resource
   * *
   * @param drawableResId the id of the drawable resource
   * *
   * @return the URL of the drawable
   */
  fun getUrlForDrawable(context: Context, @DrawableRes drawableResId: Int) = String.format(
      Locale.US,
      "%s://%s/%s/%s",
      ContentResolver.SCHEME_ANDROID_RESOURCE,
      context.resources.getResourcePackageName(drawableResId),
      context.resources.getResourceTypeName(drawableResId),
      context.resources.getResourceEntryName(drawableResId)
  )
  
  /**
   * Validates that the field is not empty and, if it's an email field, validates that the email given is valid.
   * A field is said to be an email field if it's input type is TYPE_TEXT_VARIATION_EMAIL_ADDRESS.
   *
   * @param editText the [EditText] instance to validate
   * *
   * @return true if the field has content and, in case of an email field, that the content matches a valid email
   */
  fun isValidField(editText: EditText) = !editText.text.toString().isEmpty()
      && (!isEmailField(editText) || isValidEmail(editText.text))
  
  /**
   * Checks whether the given `EditText` is an email field or not.
   *
   * @param editText the [EditText] to check
   * *
   * @return true if the [EditText] input type is TYPE_TEXT_VARIATION_EMAIL_ADDRESS
   */
  fun isEmailField(editText: EditText) = editText.inputType and
      InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS == InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
  
  /**
   * Checks whether the given [CharSequence] is a valid email address.
   *
   * @param target the [CharSequence] to check
   * *
   * @return true if the `target` is a valid email
   */
  fun isValidEmail(target: CharSequence) = !TextUtils.isEmpty(target)
      && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
  
  /**
   * Retrieves the [TextInputLayout] of the given `editText`.
   *
   * @param editText the [EditText] instance from which the [TextInputLayout] will be retrieved
   * *
   * @return the [TextInputLayout] of the `editText`
   */
  fun getTextInputLayout(editText: EditText): TextInputLayout? {
    if (editText.parent is TextInputLayout) {
      return editText.parent as TextInputLayout
    } else if (editText.parent.parent is TextInputLayout) {
      return editText.parent.parent as TextInputLayout
    }
    return null
  }
  
  /**
   * Validates that the given `fields` and put the appropriate error messages if needed.
   * The method [.validateField] is applied to each of the `fields`.
   *
   * @param fields the fields to validate
   * *
   * @param isValid function that validates the field and returns a `Pair<Boolean, String>` indicating whether or
   * *                not the field is valid and, if not, the error to be shown (if any).
   * *                If the returned message is `null`, then the message will be hidden.
   */
  fun validateFieldsAndPutErrorMessages(fields: List<EditText>, isValid: (String) -> Pair<Boolean, String>) =
      fields.forEach { UiHelper.validateField(it, isValid) }
  
  /**
   * Validates that `editText` is valid and sets the error in the [TextInputLayout], if any.
   *
   * @param editText the [EditText] to validate
   * *
   * @param isValid function that validates the field and returns a `Pair<Boolean, String>` indicating whether or
   * *                not the field is valid and, if not, the error to be shown (if any).
   * *                If the returned message is `null`, then the message will be hidden.
   * *
   * @return true if `editText` is valid using the `isValid` function
   */
  fun validateField(editText: EditText, isValid: (String) -> Pair<Boolean, String>): Boolean {
    val validation = isValid(editText.text.toString())
    UiHelper.getTextInputLayout(editText)?.let {
      it.error = if (validation.first == true) null else validation.second
      it.isErrorEnabled = validation.second == null
    }
    return validation.first ?: false
  }
}
