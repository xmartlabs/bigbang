package com.scottruth.timeoffandroid.module;

import com.scottruth.timeoffandroid.helper.GsonHelper;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by santiago on 31/08/15.
 */
@Module
public class RestServiceModule {
    private static final GsonConverterFactory GSON_CONVERTER_FACTORY = GsonConverterFactory.create(GsonHelper.getGson());

    public RestServiceModule() {
//        OkHttpClient okHttpClient = OkHttpClientFactory.createServiceOkHttpClient();
//
//        RxJavaCallAdapterFactory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(ThreenderApplication.getContext().getString(R.string.url_service))
//                .client(okHttpClient)
//                .addConverterFactory(GSON_CONVERTER_FACTORY)
//                .addCallAdapterFactory(rxJavaCallAdapterFactory)
//                .build();
//
//        authService = retrofit.create(AuthService.class);
//        layerAuthService = retrofit.create(LayerAuthService.class);
//        lookaroundService = retrofit.create(LookaroundService.class);
//        matchesService = retrofit.create(MatchesService.class);
    }

//    @Provides
//    @Singleton
//    public AuthService provideAuthService() {
//        return authService;
//    }
}
