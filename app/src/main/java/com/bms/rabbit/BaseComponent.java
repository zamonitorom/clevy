package com.bms.rabbit;
// Created by Konstantin on 13.08.2018.


import android.content.Context;

import com.bms.rabbit.entities.LessonItem;
import com.bms.rabbit.features.lesson.LessonRepository;
import com.bms.rabbit.features.auth.AuthDbDataSource;
import com.bms.rabbit.features.auth.AuthInterceptor;
import com.bms.rabbit.features.auth.AuthNetworkDataSource;
import com.bms.rabbit.features.auth.AuthRepository;
import com.bms.rabbit.features.auth.AuthRepositoryImpl;
import com.bms.rabbit.features.lesson.LessonViewModel;
import com.bms.rabbit.tools.Messenger;
import com.bms.rabbit.tools.RxErrorHandlingCallAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseComponent {
    private Router router = new RouterImpl();
    private AuthDbDataSource authDbDataSource;
    private Context context;
    private Messenger messenger;
    private Gson gson;

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

    public Messenger getMessenger() {
        if (messenger == null) {
            messenger = new Messenger(context);
        }
        return messenger;
    }

    public AuthDbDataSource getAuthDbDataSource() {
        return authDbDataSource;
    }

    public LessonViewModel getLessonViewModel() {
        return lessonViewModel;
    }

    public LessonViewModel getLessonViewModel(int lessonId) {
        if (lessonViewModel != null) {
            if (lessonViewModel.getLessonId() == lessonId) {
                return lessonViewModel;
            }
        }

        lessonViewModel = new LessonViewModel(router, getLessonRepository(), lessonId);
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
        return "http://80.87.201.72/";
    }

    public Retrofit getRetrofit() {
        return getRetrofit(getBaseClient(), getBaseUrl());
    }

    public Retrofit getSighnedRetrofit() {
        return getRetrofit(getSighnedClient(authDbDataSource), getBaseUrl());
    }

    private Retrofit getRetrofit(OkHttpClient client, String baseUrl) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .baseUrl(baseUrl)
                .client(client)
                .build();
    }

    public Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder()
//                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();
        }
        return gson;
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
