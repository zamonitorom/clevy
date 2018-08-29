package com.bms.rabbit.features.auth;
// Created by Konstantin on 15.08.2018.


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private static final String ACCESS_TOKEN_HEADER = "Authorization";
    private AuthDbDataSource authDbDataSource;

    public AuthInterceptor(AuthDbDataSource authDbDataSource) {
        this.authDbDataSource = authDbDataSource;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();
        if (authDbDataSource.getToken() != null && chain.request().header(ACCESS_TOKEN_HEADER) == null) {
            requestBuilder.addHeader(ACCESS_TOKEN_HEADER, authDbDataSource.getToken());
        }
        return chain.proceed(requestBuilder.build());
    }
}
