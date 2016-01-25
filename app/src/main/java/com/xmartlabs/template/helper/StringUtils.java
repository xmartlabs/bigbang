package com.xmartlabs.template.helper;

import android.support.annotation.Nullable;

/**
 * Created by remer on 10/5/15.
 */
public class StringUtils {
    public static boolean stringIsNullOrEmpty(@Nullable String string) {
        return string == null || string.isEmpty();
    }
}
