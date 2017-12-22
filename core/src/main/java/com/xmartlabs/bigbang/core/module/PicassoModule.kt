package com.xmartlabs.bigbang.core.module

import android.content.Context
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import com.xmartlabs.bigbang.core.log.LoggerTree
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.util.HashMap
import javax.inject.Named
import javax.inject.Singleton

@Module
open class PicassoModule {
  companion object {
    private const val LOGGER_KEY_URL = "url"
    private const val LOGGER_KEY_MESSAGE = "message"
  }
  
  @Provides
  @Singleton
  open fun providePicasso(picassoBuilder: Picasso.Builder) = picassoBuilder.build()

  @Provides
  @Singleton
  open fun providePicassoBuilder(loggerTree: LoggerTree, context: Context, downloader: OkHttp3Downloader) =
      Picasso.Builder(context)
          .downloader(downloader)
          .listener(getPicassoListener(loggerTree))
  
  private fun getPicassoListener(loggerTree: LoggerTree) = Picasso.Listener { _, uri, exception ->
    val data = HashMap<String, String>()
    uri?.let { data.put(LOGGER_KEY_URL, it.toString()) }
    data.put(LOGGER_KEY_MESSAGE, "Picasso image load failed")
    loggerTree.log(data, exception)
  }

  @Provides
  @Singleton
  open fun provideOkHttpDownloader(@Named(OkHttpModule.Companion.CLIENT_PICASSO) client: OkHttpClient) =
      OkHttp3Downloader(client)
}
