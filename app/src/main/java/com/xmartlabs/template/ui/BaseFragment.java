package com.xmartlabs.template.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.trello.rxlifecycle.components.support.RxFragment;
import com.xmartlabs.template.BaseProjectApplication;
import com.xmartlabs.template.R;

import butterknife.ButterKnife;

/**
 * Created by santiago on 15/09/15.
 */
public abstract class BaseFragment extends RxFragment {
  @Nullable
  private ProgressDialog progressDialog;

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
    ButterKnife.bind(this, view);

    return view;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    progressDialog = new ProgressDialog(activity);
    progressDialog.setCancelable(false);
    progressDialog.setMessage(getString(R.string.loading));
  }

  @Override
  public void onDetach() {
    super.onDetach();
    if (progressDialog != null) {
      progressDialog.dismiss();
    }
    progressDialog = null;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  protected void showAlertError(int stringResId) {
    new AlertDialog.Builder(getContext())
        .setMessage(stringResId)
        .setNeutralButton(android.R.string.ok, null)
        .show();
  }

  public void showProgressDialog() {
    if (progressDialog != null) {
      progressDialog.show();
    }
  }

  public void hideProgressDialog() {
    if (progressDialog != null) {
      progressDialog.hide();
    }
  }

  protected void removeItselfFromActivity() {
    ((BaseAppCompatActivity) getActivity()).removeFragment(this);
  }

  protected void removeItselfFromParentFragment() {
    getParentFragment().getChildFragmentManager().beginTransaction().remove(this).commit();
  }

  protected void removeItselfFromParent() {
    if (getParentFragment() == null) {
      removeItselfFromActivity();
    } else {
      removeItselfFromParentFragment();
    }
  }
}
