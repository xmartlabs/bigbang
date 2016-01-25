package com.xmartlabs.template.module;

import com.xmartlabs.template.R;
import com.xmartlabs.template.BaseProjectApplication;
import com.xmartlabs.template.helper.GsonHelper;
import com.xmartlabs.template.service.AuthService;
import com.xmartlabs.template.service.demo.DemoService;
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
                .baseUrl(BaseProjectApplication.getContext().getString(R.string.url_service))
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
