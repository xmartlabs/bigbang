package com.xmartlabs.template.ui;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by santiago on 20/10/15.
 */
public abstract class ValidatableFragment extends BaseFragment {
  @Getter
  @Setter
  boolean submitOnStop = true;

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
