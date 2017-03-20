package com.xmartlabs.template.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Optional;
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.trello.rxlifecycle.components.support.RxFragment;
import com.xmartlabs.template.BaseProjectApplication;
import com.xmartlabs.template.ui.common.BaseProgressDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Base Fragment implementation with the following functionality:
 * <ul>
 *   <li>Inflate the view given a layout resource</li>
 *   <li>Bind the view layout elements with ButterKnife</li>
 *   <li>Ability to show/hide a progress dialog of your choosing (providing it extends from <code>BaseProgressDialog</code></li>
 *   <li>If the activity that holds this Fragment extends from <code>BaseAppCompatActivity</code>, allows the instance
 *       to be removed</li>
 *   <li>Ability to remove itself from parent fragment</li>
 *   <li>Proper cleanup on detach/destroy</li>
 * </ul>
 */
public abstract class BaseFragment extends RxFragment {
  private Unbinder unbinder;
  @Nullable
  private BaseProgressDialog progressDialog;

  /**
   * Used to inflate the view layout/elements
   * @return the layout resource from which to inflate the view
   */
  @LayoutRes
  protected abstract int getLayoutResId();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    FragmentArgs.inject(this);
    BaseProjectApplication.getContext().inject(this);
  }

  @NonNull
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(getLayoutResId(), container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    progressDialog = createProgressDialog();
  }

  /**
   * To be used to show/hide a progress dialog.
   * The dialog must extend from BaseProgressDialog
   * @return the BaseProgressDialog instance to be shown upon request
   */
  @Nullable
  protected BaseProgressDialog createProgressDialog() {
    return null;
  }

  @Override
  public void onDetach() {
    super.onDetach();
    Optional.ofNullable(progressDialog)
        .ifPresent(BaseProgressDialog::dismiss);
    progressDialog = null;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  /**
   * Show a simple alert with an ok button
   * @param stringResId the message to be shown in the alert
   */
  protected void showAlertError(int stringResId) {
    new AlertDialog.Builder(getContext())
        .setMessage(stringResId)
        .setNeutralButton(android.R.string.ok, null)
        .show();
  }

  /**
   * Shows a progress dialog, provided the method <code>createProgressDialog</code> return an object that isn't null
   */
  public void showProgressDialog() {
    Optional.ofNullable(progressDialog)
        .ifPresent(BaseProgressDialog::show);
  }

  /**
   * Hides the progress dialog shown with <code>showProgressDialog</code>, if such a dialog was ever shown (and exists)
   */
  public void hideProgressDialog() {
    Optional.ofNullable(progressDialog)
        .ifPresent(BaseProgressDialog::hide);
  }

  /**
   * Removes the Fragment from the <code>BaseAppCompatActivity</code> Activity
   */
  protected void removeItselfFromActivity() {
    ((BaseAppCompatActivity) getActivity()).removeFragment(this);
  }

  /**
   * Removes the Fragment from it's parent one
   */
  protected void removeItselfFromParentFragment() {
    getParentFragment().getChildFragmentManager().beginTransaction().remove(this).commit();
  }

  /**
   * If the Fragment is contained within another Fragment, removes this from that Fragment
   * In other case, removes the Fragment from the <code>BaseAppCompatActivity</code> Activity
   */
  protected void removeItselfFromParent() {
    Optional.ofNullable(getParentFragment())
        .ifPresentOrElse(parent -> removeItselfFromParentFragment(), this::removeItselfFromActivity);
  }
}
