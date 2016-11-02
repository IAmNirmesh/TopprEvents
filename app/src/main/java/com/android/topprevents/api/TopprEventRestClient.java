package com.android.topprevents.api;

import android.content.Context;

import com.android.topprevents.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TopprEventRestClient {
    private static final long CACHE_SIZE = 2 * 1024 * 1024; //2MB
    private static String BASE_URL = "https://hackerearth.0x10.info/";
    private static Retrofit restAdapter;
    private static TopprEventRestClient restClient = new TopprEventRestClient();
    public static TopperEventService topperEventService;
    public static Context mContext;

    public static TopprEventRestClient getInstance(Context context) {
        mContext = context;
        return restClient;
    }

    private static Retrofit getRestClient() {
        if(null == restAdapter) {
            Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC).serializeNulls()
                    .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'").create();
            Cache cache = new Cache(new File(mContext.getCacheDir(), "http"), CACHE_SIZE);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.readTimeout(90, TimeUnit.SECONDS);
            httpClient.connectTimeout(90, TimeUnit.SECONDS);
            httpClient.cache(cache);

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                httpClient.addInterceptor(logging);
            }
            OkHttpClient client = httpClient.build();

            Retrofit.Builder builder = new Retrofit.Builder();
            builder.baseUrl(BASE_URL);
            builder.client(client);
            builder.addConverterFactory(GsonConverterFactory.create(gson));
            restAdapter = builder.build();
        }

        return restAdapter;
    }

    public TopperEventService getTopperEventService() {
        if (topperEventService == null)
            topperEventService = getRestClient().create(TopperEventService.class);

        return topperEventService;
    }
}
