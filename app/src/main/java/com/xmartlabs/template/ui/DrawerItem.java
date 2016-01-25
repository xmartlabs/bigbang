package com.xmartlabs.template.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xmartlabs.template.R;
import com.xmartlabs.template.BaseProjectApplication;

import org.parceler.Parcel;

/**
 * Created by santiago on 21/10/15.
 */
@Parcel
public enum DrawerItem {
    // TODO: define here the ites added to the drawer menu. Use next definitions as examples.
    HOME(BaseProjectApplication.getContext().getString(R.string.home), R.drawable.ic_action_action_home, DrawerItemType.MAIN, R.drawable.ic_action_action_home_inactive),
    REPOS(BaseProjectApplication.getContext().getString(R.string.public_repos_title), R.drawable.ic_action_action_polymer, DrawerItemType.MAIN, R.drawable.ic_action_action_polymer_inactive),
    __DIVIDED__(null, null, DrawerItemType.DIVIDER),
    SETTINGS(BaseProjectApplication.getContext().getString(R.string.settings), null, DrawerItemType.SECONDARY);

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
        drawerItemType = DrawerItemType.MAIN;
        inactiveDrawableResId = null;
        selectable = true;
    }

    DrawerItem(@Nullable String text, @Nullable Integer drawableResId, @NonNull DrawerItemType drawerItemType) {
        this.text = text;
        this.drawableResId = drawableResId;
        this.drawerItemType = drawerItemType;
        inactiveDrawableResId = null;
        selectable = drawerItemType == DrawerItemType.MAIN;
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
