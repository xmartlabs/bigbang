package com.xmartlabs.template.gcm;

import android.app.IntentService;
import android.content.Intent;

import com.annimon.stream.Objects;
import com.annimon.stream.Stream;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.xmartlabs.template.BaseProjectApplication;
import com.xmartlabs.template.controller.AuthController;

import java.io.IOException;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by mirland on 12/04/16.
 */
public class RegistrationIntentService extends IntentService {
  // TODO mirland: 4/18/16 add senders id in the keys file
  private static final String DEFAULT_SENDER_ID = ""; // getContext().getString(R.string.gcm_defaultSenderId);
  private static final String[] SENDERS = {}; // getContext().getResources().getStringArray(R.array.gcm_senders_id);

  @Inject
  AuthController authController;

  public RegistrationIntentService() {
    super(RegistrationIntentService.class.getName());
    BaseProjectApplication.getContext().inject(this);
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    try {
      InstanceID instanceID = InstanceID.getInstance(this);
      String serviceToken = instanceID.getToken(DEFAULT_SENDER_ID, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
      Stream.of(SENDERS)
          .filter(sender -> !Objects.equals(sender, DEFAULT_SENDER_ID))
          .forEach(sender -> {
            try {
              String token = instanceID.getToken(sender, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
              Timber.d("GCM Registration Token of sender %s is %s", sender, token);
            } catch (IOException e) {
              Timber.d("Failed to complete token refresh");
            }
          });
      authController.registerGcmToken(serviceToken);
    } catch (Exception e) {
      Timber.d("Failed to complete token refresh");
    }
  }

  private static BaseProjectApplication getContext() {
    return BaseProjectApplication.getContext();
  }
}