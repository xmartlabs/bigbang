package com.xmartlabs.template.ui;

/**
 * Created by santiago on 20/10/15.
 */
public abstract class ValidatableFragment extends BaseFragment {
  private boolean submitOnStop = true;

  @SuppressWarnings("unused")
  public boolean isSubmitOnStop() {
    return submitOnStop;
  }

  public void setSubmitOnStop(boolean submitOnStop) {
    this.submitOnStop = submitOnStop;
  }

  public boolean validateFields() {
    return true;
  }

  public void submit() {

  }

  @Override
  public void onStop() {
    super.onStop();
    if (submitOnStop && validateFields()) {
      submit();
    }
  }
}
