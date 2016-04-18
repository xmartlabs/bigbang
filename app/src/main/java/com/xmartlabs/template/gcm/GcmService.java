package com.xmartlabs.template.gcm;

import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;

import timber.log.Timber;

/**
 * Created by mirland on 17/03/16.
 */
public class GcmService extends GcmListenerService {
    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);
        Timber.d("Push arrived from: %s - message: %s", from, data);
    }
}