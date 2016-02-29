package com.xmartlabs.template.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.annimon.stream.Objects;
import com.annimon.stream.Stream;
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
  REPOS(BaseProjectApplication.getContext().getString(R.string.repo_list_title), R.drawable.ic_action_action_polymer, DrawerItemType.ABOVE),
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

  DrawerItem(@Nullable String text, @Nullable Integer drawableResId, @NonNull DrawerItemType drawerItemType) {
    this.text = text;
    this.drawableResId = drawableResId;
    this.drawerItemType = drawerItemType;
  }

  public int getValue() {
    return ordinal();
  }

  public boolean isSelectable() {
    return drawerItemType == DrawerItemType.ABOVE;
  }

  @Nullable
  @Override
  public String toString() {
    return text;
  }

  @NonNull
  @SuppressWarnings("unused")
  public static DrawerItem fromInteger(int value) {
    return Stream.of(values())
        .filter(drawerItem -> Objects.equals(drawerItem.getValue(), value))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(String.format(Locale.getDefault(), "Invalid drawer item value: %d", value)));
  }
}
