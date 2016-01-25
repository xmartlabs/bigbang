package com.xmartlabs.template.module;

import android.os.Build;
import android.os.StatFs;

import com.annimon.stream.Objects;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import com.xmartlabs.template.BaseProjectApplication;
import com.xmartlabs.template.BuildConfig;
import com.xmartlabs.template.BuildType;

import java.io.File;

import timber.log.Timber;

/**
 * Created by santiago on 31/08/15.
 */
public class OkHttpClientFactory {
  private static final int MIN_DISK_CACHE_SIZE = 10 * 1024 * 1024; // 1MB
  private static final int MAX_DISK_CACHE_SIZE = 100 * 1024 * 1024; // 100MB

  public static OkHttpClient createServiceOkHttpClient() {
    OkHttpClient client = new OkHttpClient();

    client.networkInterceptors().add(new SessionInterceptor());

    if (BuildConfig.DEBUG && !Objects.equals(BuildConfig.FLAVOR, BuildType.PRODUCTION.toString())) {
      HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Timber.tag("OkHttp").d(message));
      loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
      client.networkInterceptors().add(loggingInterceptor);
    }

    return client;
  }

  public static OkHttpClient createPicassoOkHttpClient() {
    OkHttpClient client = new OkHttpClient();

    if (BuildConfig.DEBUG && !Objects.equals(BuildConfig.FLAVOR, BuildType.PRODUCTION.toString())) {
      HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Timber.tag("OkHttp").d(message));
      loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
      client.networkInterceptors().add(loggingInterceptor);
    }

    File httpCacheDir = new File(BaseProjectApplication.getContext().getExternalCacheDir(), "cache");
    if (!httpCacheDir.exists()) {
      //noinspection ResultOfMethodCallIgnored
      httpCacheDir.mkdirs();
    }
    long httpCacheSize = calculateDiskCacheSize(httpCacheDir);
    Cache cache = new Cache(httpCacheDir, httpCacheSize);
    client.setCache(cache);

    return client;
  }

  // Stolen from: https://github.com/square/picasso/blob/eb2e9730fb7dbe25b4ab9d4ba5d2050c70c27024/picasso/src/main/java/com/squareup/picasso/Utils.java#L255
  static long calculateDiskCacheSize(File dir) {
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
