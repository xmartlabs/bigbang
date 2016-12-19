package com.xmartlabs.template.ui.mvp;

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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Fragment that uses a {@link MvpPresenter} to implement the MVP pattern. The fragments that inherit from this class
 * will conform the V part of the said pattern.
 *
 * @param <V> the contract that provides the public API for the presenter to invoke view related methods
 * @param <P> the presenter that coordinates the view
 */
@FragmentWithArgs
public abstract class BaseMvpFragment<V extends MvpView, P extends MvpPresenter<V>> extends RxFragment implements MvpView {
  @Getter(AccessLevel.PROTECTED)
  @Setter(AccessLevel.PROTECTED)
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
    //noinspection unchecked
    presenter.attachView((V) this);
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
    ((BaseMvpAppCompatActivity) getActivity()).removeFragment(this);
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
   * Creates the presenter instance.
   *
   * @return the created presenter instance
   */
  @NonNull
  protected abstract P createPresenter();
}
