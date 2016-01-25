package com.xmartlabs.template.ui;

import android.os.Bundle;

import com.hannesdorfmann.fragmentargs.bundler.ArgsBundler;

import org.parceler.Parcels;

/**
 * Created by santiago on 15/09/15.
 */
public abstract class ParcelableArgsBundler<T> implements ArgsBundler<T> {
  @Override
  public void put(String key, T value, Bundle bundle) {
    bundle.putParcelable(key, Parcels.wrap(value));
  }

  @Override
  public <V extends T> V get(String key, Bundle bundle) {
    return Parcels.unwrap(bundle.getParcelable(key));
  }
}
