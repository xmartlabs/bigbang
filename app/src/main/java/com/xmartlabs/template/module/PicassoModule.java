package com.xmartlabs.template.module;

import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.xmartlabs.template.helper.GeneralErrorHelper;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import timber.log.Timber;

/**
 * Created by santiago on 26/01/16.
 */
@Module
public class PicassoModule {
  private static final Picasso.Listener LISTENER = (picasso, uri, exception) -> {
    if (uri != null) {
      Crashlytics.setString(GeneralErrorHelper.CRASHLYTICS_KEY_URL, uri.toString());
    }
    Timber.w(exception, "Picasso image load failed");
  };

  @Provides
  @Singleton
  Picasso providePicasso(Picasso.Builder picassoBuilder) {
    return picassoBuilder.build();
  }

  @Provides
  @Singleton
  Picasso.Builder providePicassoBuilder(Context context, OkHttp3Downloader downloader) {
    return new Picasso.Builder(context)
        .downloader(downloader)
        //.indicatorsEnabled(BuildConfig.DEBUG)
        .listener(LISTENER);
  }

  @Provides
  @Singleton
  OkHttp3Downloader provideOkHttpDownloader(@Named(OkHttpModule.CLIENT_PICASSO) OkHttpClient client) {
    return new OkHttp3Downloader(client);
  }
}
