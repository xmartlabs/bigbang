package com.xmartlabs.template.helper;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.xmartlabs.template.BaseProjectApplication;
import com.xmartlabs.template.gcm.RegistrationIntentService;

import java.io.IOException;
import java.util.LinkedList;

import rx.Completable;
import rx.Subscription;
import rx.exceptions.CompositeException;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by mirland on 18/03/16.
 */
public class GcmHelper {
  // TODO mirland: 4/18/16 add senders id in the keys file
  private static final String[] SENDERS = {}; // getContext().getResources().getStringArray(R.array.gcm_senders_id);

  public boolean registerGCM(Context activity) {
    boolean isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getContext())
        == ConnectionResult.SUCCESS;
    if (isGooglePlayServicesAvailable) {
      Intent intent = new Intent(activity, RegistrationIntentService.class);
      activity.startService(intent);
    }
    return isGooglePlayServicesAvailable;
  }

  public static void unregisterGCMToken() {
    Completable
        .create(subscriber -> {
          CompositeException compositeException = new CompositeException(new LinkedList<>());
          for (String sender : SENDERS) {
            try {
              InstanceID.getInstance(getContext()).deleteToken(sender, GoogleCloudMessaging.INSTANCE_ID_SCOPE);
            } catch (IOException e) {
              compositeException.getExceptions().add(e);
            }
          }
          if (compositeException.getExceptions().isEmpty()) {
            subscriber.onCompleted();
          } else {
            subscriber.onError(compositeException);
          }
        })
        .subscribeOn(Schedulers.io())
        .subscribe(new Completable.CompletableSubscriber() {
          @Override
          public void onCompleted() {
            Timber.i("All gcm tokens was unregistered");
          }

          @Override
          public void onError(Throwable e) {
            Timber.e(e, null);
          }

          @Override
          public void onSubscribe(Subscription d) {

          }
        });
  }

  private static BaseProjectApplication getContext() {
    return BaseProjectApplication.getContext();
  }
}
