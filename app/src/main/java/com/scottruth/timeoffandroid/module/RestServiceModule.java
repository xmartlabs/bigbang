package com.scottruth.timeoffandroid.module;

import com.scottruth.timeoffandroid.R;
import com.scottruth.timeoffandroid.TimeOffApplication;
import com.scottruth.timeoffandroid.helper.GsonHelper;
import com.scottruth.timeoffandroid.service.AuthService;
import com.scottruth.timeoffandroid.service.demo.DemoService;
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

    private AuthService authService;

    // TODO: Just for demo, delete next line in the real project
    private DemoService demoService;

    public RestServiceModule() {
        OkHttpClient okHttpClient = OkHttpClientFactory.createServiceOkHttpClient();

        RxJavaCallAdapterFactory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TimeOffApplication.getContext().getString(R.string.url_service))
                .client(okHttpClient)
                .addConverterFactory(GSON_CONVERTER_FACTORY)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();

        authService = retrofit.create(AuthService.class);
        demoService = retrofit.create(DemoService.class);
    }

    @Provides
    @Singleton
    public AuthService provideAuthService() {
        return authService;
    }

    @Provides
    @Singleton
    public DemoService provideDemoService() {
        // TODO: Just for demo, delete this method in the real project
        return demoService;
    }
}
