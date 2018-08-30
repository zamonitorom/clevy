package com.bms.rabbit;
// Created by Konstantin on 13.08.2018.


import android.content.Context;

import com.bms.rabbit.features.LessonRepository;
import com.bms.rabbit.features.auth.AuthDbDataSource;
import com.bms.rabbit.features.auth.AuthInterceptor;
import com.bms.rabbit.features.auth.AuthNetworkDataSource;
import com.bms.rabbit.features.auth.AuthRepository;
import com.bms.rabbit.features.auth.AuthRepositoryImpl;
import com.bms.rabbit.features.lesson.LessonViewModel;
import com.bmsoftware.sense2beat.Router;
import com.bmsoftware.sense2beat.RouterImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseComponent {
    private Router router = new RouterImpl();
    private AuthDbDataSource authDbDataSource;
    private Context context;

    private LessonViewModel lessonViewModel;
    private LessonRepository lessonRepository;

    private BaseComponent() {
    }

    public static BaseComponent create(Context context) {
        BaseComponent component = new BaseComponent();
        component.authDbDataSource = new AuthDbDataSource(context);
        component.context = context;
        return component;
    }

    public Router getRouter() {
        return router;
    }

    public Context getContext() {
        return context;
    }

    public AuthDbDataSource getAuthDbDataSource() {
        return authDbDataSource;
    }

    public LessonViewModel getLessonViewModel() {
        return lessonViewModel;
    }

    public LessonViewModel getLessonViewModel(int id) {
        if (lessonViewModel != null) {
            if (lessonViewModel.getId() == id) {
                return lessonViewModel;
            }
        }

        lessonViewModel = new LessonViewModel(router, id);
        return lessonViewModel;
    }

    public AuthRepository getAuthRepository() {
        AuthNetworkDataSource authNetworkDataSource = new AuthNetworkDataSource(getRetrofit());
        return new AuthRepositoryImpl(authDbDataSource, authNetworkDataSource);
    }

    public LessonRepository getLessonRepository() {
        if (lessonRepository == null) {
            lessonRepository = new LessonRepository(getSighnedRetrofit());
        }
        return lessonRepository;
    }


    public String getBaseUrl() {
        return "https://russian-friends.com/";
    }

    public Retrofit getRetrofit() {
        return getRetrofit(getBaseClient(), getBaseUrl());
    }

    public Retrofit getSighnedRetrofit() {
        return getRetrofit(getSighnedClient(authDbDataSource), getBaseUrl());
    }

    private Retrofit getRetrofit(OkHttpClient client, String baseUrl) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .baseUrl(baseUrl)
                .client(client)
                .build();
    }

    private Gson getGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
    }

    private OkHttpClient getBaseClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(getLoggingInterceptor())
                .build();
    }

    private OkHttpClient getSighnedClient(AuthDbDataSource authDbDataSource) {
        return new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(authDbDataSource))
                .addInterceptor(getLoggingInterceptor())
                .build();
    }

    private static HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return interceptor;
    }


}
