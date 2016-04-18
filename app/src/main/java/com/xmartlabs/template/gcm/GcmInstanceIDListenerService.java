package com.xmartlabs.template.gcm;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by mirland on 26/08/15.
 */
public class GcmInstanceIDListenerService extends InstanceIDListenerService {
  @Override
  public void onTokenRefresh() {
    Intent intent = new Intent(this, RegistrationIntentService.class);
    startService(intent);
  }
}