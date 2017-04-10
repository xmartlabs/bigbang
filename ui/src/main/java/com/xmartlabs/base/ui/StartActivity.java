package com.xmartlabs.base.ui;

import android.content.Intent;
import android.os.Bundle;

public class StartActivity extends BaseActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Intent intent = Henson.with(getContext()).gotoMainActivity().build();

    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    getContext().startActivity(intent);
  }
}
