package com.joshtalks.joshtalksassignment.utils.networkUtils;

import android.app.Application;
import android.content.Context;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.joshtalks.joshtalksassignment.utils.Constants.BASE_URL;

/**
 * This is the class to create the instance
 * of Retrofit and passing the relevant data while
 * creating it.
 *
 * @author Labhya Sharma
 */
public class RetrofitClientInstance {
    private static Retrofit retrofit;

    /**
     * Method to get instance of this class
     */
    public static Retrofit getRetrofitInstance(Application application) {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(provideOkHttpClient(provideOkHttpCache(application.getCacheDir()),application))
                    .build();
        }
        return retrofit;
    }

    private static Cache provideOkHttpCache(File file) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        return new Cache(file, cacheSize);
    }

    private static OkHttpClient provideOkHttpClient(Cache cache, Context context) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client;

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().addInterceptor(interceptor).cache(cache);

        clientBuilder.readTimeout(120, TimeUnit.SECONDS);
        clientBuilder.connectTimeout(120, TimeUnit.SECONDS);
        clientBuilder.connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT));
        client=clientBuilder.build();
        return client;
    }
}
