package com.xmartlabs.template.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xmartlabs.template.BaseProjectApplication;
import com.xmartlabs.template.R;

import java.util.Locale;

import lombok.Getter;

/**
 * Created by santiago on 21/10/15.
 */
public enum DrawerItem {
  // TODO: define here the items for the drawer menu. Use next definitions as examples.
  HOME(BaseProjectApplication.getContext().getString(R.string.home), R.drawable.ic_action_action_home, DrawerItemType.ABOVE),
  REPOS(BaseProjectApplication.getContext().getString(R.string.public_repos_title), R.drawable.ic_action_action_polymer, DrawerItemType.ABOVE),
  __DIVIDED__(null, null, DrawerItemType.DIVIDER),
  SETTINGS(BaseProjectApplication.getContext().getString(R.string.settings), null, DrawerItemType.BELOW);

  @Nullable
  private final String text;
  @Getter
  @Nullable
  private final Integer drawableResId;
  @Getter
  @NonNull
  private final DrawerItemType drawerItemType;
  @Getter
  private final boolean selectable;

  DrawerItem(@Nullable String text, @Nullable Integer drawableResId, @NonNull DrawerItemType drawerItemType) {
    this.text = text;
    this.drawableResId = drawableResId;
    this.drawerItemType = drawerItemType;
    selectable = drawerItemType == DrawerItemType.ABOVE;
  }

  public int getValue() {
    return ordinal();
  }

  @Nullable
  @Override
  public String toString() {
    return text;
  }

  @NonNull
  @SuppressWarnings("unused")
  public static DrawerItem fromInteger(int value) {
    for (DrawerItem drawerItem : values()) {
      if (drawerItem.getValue() == value) {
        return drawerItem;
      }
    }
    throw new IllegalArgumentException(String.format(Locale.getDefault(), "Invalid drawer item value: %d", value));
  }
}
