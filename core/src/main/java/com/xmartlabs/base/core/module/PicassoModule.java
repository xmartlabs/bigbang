package com.xmartlabs.base.core.module;

import android.content.Context;
import android.support.annotation.NonNull;

import com.annimon.stream.Optional;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.xmartlabs.base.core.log.LoggerTree;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class PicassoModule {
  private static final String LOGGER_KEY_URL = "url";
  private static final String LOGGER_KEY_MESSAGE = "message";

  @Provides
  @Singleton
  Picasso providePicasso(Picasso.Builder picassoBuilder) {
    return picassoBuilder.build();
  }

  @Provides
  @Singleton
  Picasso.Builder providePicassoBuilder(@NonNull LoggerTree loggerTree, @NonNull Context context,
                                        @NonNull OkHttp3Downloader downloader) {
    return new Picasso.Builder(context)
        .downloader(downloader)
        .listener(getPicassoListener(loggerTree));
  }

  private Picasso.Listener getPicassoListener(@NonNull LoggerTree loggerTree) {
    return (picasso, uri, exception) -> {
      Map<String, String> data = new HashMap<>();
      Optional.ofNullable(uri)
          .ifPresent(theUri -> data.put(LOGGER_KEY_URL, theUri.toString()));
      data.put(LOGGER_KEY_MESSAGE, "Picasso image load failed");
      loggerTree.log(data, exception);
    };
  }

  @Provides
  @Singleton
  OkHttp3Downloader provideOkHttpDownloader(@Named(OkHttpModule.CLIENT_PICASSO) OkHttpClient client) {
    return new OkHttp3Downloader(client);
  }
}
