package com.xmartlabs.template.module;

import android.os.Build;
import android.os.StatFs;
import android.support.annotation.NonNull;

import com.annimon.stream.Objects;
import com.moczul.ok2curl.CurlInterceptor;
import com.xmartlabs.template.BaseProjectApplication;
import com.xmartlabs.template.BuildConfig;
import com.xmartlabs.template.BuildType;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

/**
 * Created by santiago on 31/08/15.
 */
@Module
public class OkHttpModule {
  private static final int MIN_DISK_CACHE_SIZE = 10 * 1024 * 1024; // 10MB
  private static final int MAX_DISK_CACHE_SIZE = 100 * 1024 * 1024; // 100MB
  public static final String CLIENT_PICASSO = "Picasso";
  public static final String CLIENT_SERVICE = "Service";

  @Named(CLIENT_SERVICE)
  @Provides
  @Singleton
  OkHttpClient provideServiceOkHttpClient(OkHttpClient.Builder clientBuilder) {
    addLoggingInterceptor(clientBuilder);

    return clientBuilder.build();
  }

  @Named(CLIENT_PICASSO)
  @Provides
  @Singleton
  OkHttpClient providePicassoOkHttpClient(OkHttpClient.Builder clientBuilder, Cache cache) {
    clientBuilder.cache(cache);

    addLoggingInterceptor(clientBuilder);

    return clientBuilder.build();
  }

  @Provides
  OkHttpClient.Builder provideOkHttpClientBuilder() {
    return new OkHttpClient.Builder();
  }

  @Provides
  @Singleton
  Cache provideCache() {
    File httpCacheDir = new File(BaseProjectApplication.getContext().getExternalCacheDir(), "cache");
    if (!httpCacheDir.exists()) {
      //noinspection ResultOfMethodCallIgnored
      httpCacheDir.mkdirs();
    }
    long httpCacheSize = calculateDiskCacheSize(httpCacheDir);
    return new Cache(httpCacheDir, httpCacheSize);
  }

  private void addLoggingInterceptor(@NonNull OkHttpClient.Builder clientBuilder) {
    if (BuildConfig.DEBUG && !Objects.equals(BuildConfig.FLAVOR, BuildType.PRODUCTION.toString())) {
      HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Timber.tag("OkHttp").d(message));
      loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
      clientBuilder.addInterceptor(loggingInterceptor);

      CurlInterceptor curlInterceptor = new CurlInterceptor();
      clientBuilder.addInterceptor(curlInterceptor);
    }
  }

  // Stolen from: https://github.com/square/picasso/blob/eb2e9730fb7dbe25b4ab9d4ba5d2050c70c27024/picasso/src/main/java/com/squareup/picasso/Utils.java#L255
  private long calculateDiskCacheSize(File dir) {
    long size = MIN_DISK_CACHE_SIZE;

    try {
      StatFs statFs = new StatFs(dir.getAbsolutePath());
      long blockCount;
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
        blockCount = statFs.getBlockCountLong();
      } else {
        //noinspection deprecation
        blockCount = (long) statFs.getBlockCount();
      }
      long available = blockCount * blockCount;
      // Target 2% of the total space.
      size = available / 50;
    } catch (IllegalArgumentException e) {
      Timber.e(e, "Error while trying to calculate disk cache size");
    }

    // Bound inside min/max size for disk cache.
    return Math.max(Math.min(size, MAX_DISK_CACHE_SIZE), MIN_DISK_CACHE_SIZE);
  }
}
