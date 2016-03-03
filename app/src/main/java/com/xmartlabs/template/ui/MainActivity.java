package com.xmartlabs.template.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.f2prateek.dart.*;
import com.xmartlabs.template.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by santiago on 03/03/16.
 */
@HensonNavigable
public class MainActivity extends BaseAppCompatActivity {
  @Bind(R.id.toolbar)
  Toolbar toolbar;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    setSupportActionBar(toolbar);
  }
}
