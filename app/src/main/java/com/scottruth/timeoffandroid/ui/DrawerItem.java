package com.scottruth.timeoffandroid.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.scottruth.timeoffandroid.R;
import com.scottruth.timeoffandroid.TimeOffApplication;

import org.parceler.Parcel;

/**
 * Created by santiago on 21/10/15.
 */
@Parcel
public enum DrawerItem {
    // TODO: define here the ites added to the drawer menu. Use next definitions as examples.
    HOME(TimeOffApplication.getContext().getString(R.string.home), R.drawable.ic_action_action_home, DrawerItemType.ABOVE, R.drawable.ic_action_action_home_inactive),
    REPOS(TimeOffApplication.getContext().getString(R.string.public_repos_title), R.drawable.ic_action_action_polymer, DrawerItemType.ABOVE, R.drawable.ic_action_action_polymer_inactive),
    __DIVIDED__(null, null, DrawerItemType.DIVIDER),
    SETTINGS(TimeOffApplication.getContext().getString(R.string.settings), null, DrawerItemType.BELOW);

    @Nullable
    private final String text;
    @Nullable
    private final Integer drawableResId;
    private final DrawerItemType drawerItemType;
    @Nullable
    private Integer inactiveDrawableResId;
    private final boolean selectable;
    private int newMessagesCount = 0;

    @SuppressWarnings("unused")
    DrawerItem() { // For Parceler
        text = null;
        drawableResId = null;
        drawerItemType = DrawerItemType.ABOVE;
        inactiveDrawableResId = null;
        selectable = true;
    }

    DrawerItem(@Nullable String text, @Nullable Integer drawableResId, @NonNull DrawerItemType drawerItemType) {
        this.text = text;
        this.drawableResId = drawableResId;
        this.drawerItemType = drawerItemType;
        inactiveDrawableResId = null;
        selectable = drawerItemType == DrawerItemType.ABOVE;
    }

    DrawerItem(@Nullable String text, @Nullable Integer drawableResId, @NonNull DrawerItemType drawerItemType, int inactiveDrawableResId) {
        this(text, drawableResId, drawerItemType);
        this.inactiveDrawableResId = inactiveDrawableResId;
    }

    public int getValue() {
        return ordinal();
    }

    @Nullable
    public Integer getDrawableResId() {
        return drawableResId;
    }

    @NonNull
    public DrawerItemType getDrawerItemType() {
        return drawerItemType;
    }

    @Nullable
    public Integer getInactiveDrawableResId() {
        return inactiveDrawableResId;
    }

    public boolean getSelectable() {
        return selectable;
    }

    public int getNewMessagesCount() {
        return newMessagesCount;
    }

    public void setNewMessagesCount(int newMessagesCount) {
        this.newMessagesCount = newMessagesCount;
    }

    @Nullable
    @Override
    public String toString() {
        return text;
    }

    @NonNull
    public static DrawerItem fromInteger(int value) {
        for (DrawerItem drawerItem : values()) {
            if (drawerItem.getValue() == value) {
                return drawerItem;
            }
        }
        throw new IllegalArgumentException("Invalid drawer item value");
    }
}
