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
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.trello.rxlifecycle.components.support.RxFragment;
import com.xmartlabs.template.BaseProjectApplication;
import com.xmartlabs.template.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment that uses a {@link IPresenter} to implement the MVP pattern
 * The fragments that inherit from this class will conform the V part of the said pattern
 * @param <V> The contract that provides the public API for the presenter to invoke view related methods
 * @param <P> The presenter that coordinates the view
 */
@FragmentWithArgs
public abstract class BaseFragment<V extends IView, P extends IPresenter<V>> extends RxFragment implements IView {
  private P presenter;

  private Unbinder unbinder;
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
    unbinder = ButterKnife.bind(this, view);
    presenter = createPresenter();
    presenter.attachView((V)this);
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
    presenter.detachView();
    unbinder.unbind();
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

  /**
   * Returns the presenter instance
   * @return the presenter instance
   */
  public P getPresenter() {
    return presenter;
  }

  /**
   * Sets the presenter instance
   * @param presenter the presenter instance. It cannot be null.
   */
  public void setPresenter(@NonNull P presenter) {
    this.presenter = presenter;
  }

  /**
   * Creates the presenter instance
   *
   * @return the created presenter instance
   */
  @NonNull
  protected abstract P createPresenter();
}
